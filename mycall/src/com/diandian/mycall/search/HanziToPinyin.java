package com.diandian.mycall.search;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Locale;

import android.text.TextUtils;
import android.util.Log;

public class HanziToPinyin {

	private static final String TAG = "HanziToPinyin";

	// Turn on this flag when we want to check internal data structure.
	private static final boolean DEBUG = false;

	/**
	 * First and last Chinese character with known Pinyin according to zh
	 * collation
	 */
	private static final String FIRST_PINYIN_UNIHAN = "\u963F";
	private static final String LAST_PINYIN_UNIHAN = "\u9FFF";

	private static final Collator COLLATOR = Collator.getInstance(Locale.CHINA);

	private static HanziToPinyin sInstance;
	private final boolean mHasChinaCollator;

	public static class Token {
		/**
		 * Separator between target string for each source char
		 */
		public static final String SEPARATOR = " ";

		public static final int LATIN = 1;
		public static final int PINYIN = 2;
		public static final int UNKNOWN = 3;

		public Token() {
		}

		public Token(int type, String source, String target) {
			this.type = type;
			this.source = source;
			this.target = target;
		}

		/**
		 * Type of this token, ASCII, PINYIN or UNKNOWN.
		 */
		public int type;
		/**
		 * Original string before translation.
		 */
		public String source;
		/**
		 * Translated string of source. For Han, target is corresponding Pinyin.
		 * Otherwise target is original string in source.
		 */
		public String target;
	}

	protected HanziToPinyin(boolean hasChinaCollator) {
		mHasChinaCollator = hasChinaCollator;
	}

	public static HanziToPinyin getInstance() {
		synchronized (HanziToPinyin.class) {
			if (sInstance != null) {
				return sInstance;
			}
			// Check if zh_CN collation data is available
			final Locale locale[] = Collator.getAvailableLocales();
			for (int i = 0; i < locale.length; i++) {
				if (locale[i].equals(Locale.CHINA)) {
					// Do self validation just once.
					if (DEBUG) {
						Log.d(TAG, "Self validation. Result: "
								+ doSelfValidation());
					}
					sInstance = new HanziToPinyin(true);
					return sInstance;
				}
			}
			Log.w(TAG,
					"There is no Chinese collator, HanziToPinyin is disabled");
			sInstance = new HanziToPinyin(false);
			return sInstance;
		}
	}

	/**
	 * Validate if our internal table has some wrong value.
	 * 
	 * @return true when the table looks correct.
	 */
	private static boolean doSelfValidation() {
		char lastChar = DataSouces.UNIHANS[0];
		String lastString = Character.toString(lastChar);
		for (char c : DataSouces.UNIHANS) {
			if (lastChar == c) {
				continue;
			}
			final String curString = Character.toString(c);
			int cmp = COLLATOR.compare(lastString, curString);
			if (cmp >= 0) {
				Log.e(TAG, "Internal error in Unihan table. "
						+ "The last string \"" + lastString
						+ "\" is greater than current string \"" + curString
						+ "\".");
				return false;
			}
			lastString = curString;
		}
		return true;
	}

	private Token getToken(char character) {
		Token token = new Token();
		final String letter = Character.toString(character);
		token.source = letter;
		int offset = -1;
		int cmp;
		if (character < 256) {
			token.type = Token.LATIN;
			token.target = letter;
			return token;
		} else {
			cmp = COLLATOR.compare(letter, FIRST_PINYIN_UNIHAN);
			if (cmp < 0) {
				token.type = Token.UNKNOWN;
				token.target = letter;
				return token;
			} else if (cmp == 0) {
				token.type = Token.PINYIN;
				offset = 0;
			} else {
				cmp = COLLATOR.compare(letter, LAST_PINYIN_UNIHAN);
				if (cmp > 0) {
					token.type = Token.UNKNOWN;
					token.target = letter;
					return token;
				} else if (cmp == 0) {
					token.type = Token.PINYIN;
					offset = DataSouces.UNIHANS.length - 1;
				}
			}
		}

		token.type = Token.PINYIN;
		if (offset < 0) {
			int begin = 0;
			int end = DataSouces.UNIHANS.length - 1;
			while (begin <= end) {
				offset = (begin + end) / 2;
				final String unihan = Character
						.toString(DataSouces.UNIHANS[offset]);
				cmp = COLLATOR.compare(letter, unihan);
				if (cmp == 0) {
					break;
				} else if (cmp > 0) {
					begin = offset + 1;
				} else {
					end = offset - 1;
				}
			}
		}
		if (cmp < 0) {
			offset--;
		}
		StringBuilder pinyin = new StringBuilder();
		for (int j = 0; j < DataSouces.PINYINS[offset].length
				&& DataSouces.PINYINS[offset][j] != 0; j++) {
			pinyin.append((char) DataSouces.PINYINS[offset][j]);
		}
		token.target = pinyin.toString();
		if (TextUtils.isEmpty(token.target)) {
			token.type = Token.UNKNOWN;
			token.target = token.source;
		}
		return token;
	}

	public ArrayList<Token> get(final String input) {
		ArrayList<Token> tokens = new ArrayList<Token>();
		if (!mHasChinaCollator || TextUtils.isEmpty(input)) {
			// return empty tokens.
			return tokens;
		}
		final int inputLength = input.length();
		final StringBuilder sb = new StringBuilder();
		int tokenType = Token.LATIN;
		// Go through the input, create a new token when
		// a. Token type changed
		// b. Get the Pinyin of current charater.
		// c. current character is space.
		for (int i = 0; i < inputLength; i++) {
			final char character = input.charAt(i);
			if (character == ' ') {
				if (sb.length() > 0) {
					addToken(sb, tokens, tokenType);
				}
			} else if (character < 256) {
				if (tokenType != Token.LATIN && sb.length() > 0) {
					addToken(sb, tokens, tokenType);
				}
				tokenType = Token.LATIN;
				sb.append(character);
			} else {
				Token t = getToken(character);
				if (t.type == Token.PINYIN) {
					if (sb.length() > 0) {
						addToken(sb, tokens, tokenType);
					}
					tokens.add(t);
					tokenType = Token.PINYIN;
				} else {
					if (tokenType != t.type && sb.length() > 0) {
						addToken(sb, tokens, tokenType);
					}
					tokenType = t.type;
					sb.append(character);
				}
			}
		}
		if (sb.length() > 0) {
			addToken(sb, tokens, tokenType);
		}
		return tokens;
	}

	private void addToken(final StringBuilder sb,
			final ArrayList<Token> tokens, final int tokenType) {
		String str = sb.toString();
		tokens.add(new Token(tokenType, str, str));
		sb.setLength(0);
	}

}