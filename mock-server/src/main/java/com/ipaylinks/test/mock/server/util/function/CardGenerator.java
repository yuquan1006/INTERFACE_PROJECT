package com.ipaylinks.test.mock.server.util.function;

import java.util.List;
import java.util.Stack;
import java.util.Vector;

/**
 * 随机信用卡号生成器
 *
 * @author QiuHua Yang
 * @version Created on 2017/12/23
 */
public class CardGenerator {

    public static final String[] VISA_PREFIX_LIST = new String[] { "4539",
            "4556", "4916", "4532", "4929", "40240071", "4485", "4716", "4" };

    public static final String[] MASTERCARD_PREFIX_LIST = new String[] { "51",
            "52", "53", "54", "55" };

    public static final String[] AE_PREFIX_LIST = new String[] { "34", "37" };

    public static final String[] DISCOVER_PREFIX_LIST = new String[] { "6011" };

    public static final String[] DINERS_CLUB_PREFIX_LIST = new String[] { "300",
            "301", "302", "303", "36", "38" };

    public static final String[] ENROUTE_PREFIX_LIST = new String[] { "2014",
            "2149" };

    public static final String[] JCB_PREFIX_LIST = new String[] { "180000" };

    public static final String[] VOYAGER_PREFIX_LIST = new String[] { "8699" };

    private static String reverse(String str) {
        if (str == null){
            return "";
        }
        String result = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            result += str.charAt(i);
        }
        return result;
    }

    /**
     *
     * 'prefix' is the start of the CC number as a string, any number of digits.
     * 'length' is the length of the CC number to generate. Typically 13 or 16
     */
    private static String completedNumber(String prefix, int length) {

        String cardNumber = prefix;

        while (cardNumber.length() < (length - 1)) {
            cardNumber += new Double(Math.floor(Math.random() * 10)).intValue();
        }

        String reversedCreditCardNumberString = reverse(cardNumber);

        List<Integer> reversedCreditCardNumberList = new Vector<>();
        for (int i = 0; i < reversedCreditCardNumberString.length(); i++) {
            reversedCreditCardNumberList.add(new Integer(String
                    .valueOf(reversedCreditCardNumberString.charAt(i))));
        }

        int sum = 0;
        int pos = 0;

        Integer[] reversedCreditCardNumber = reversedCreditCardNumberList
                .toArray(new Integer[reversedCreditCardNumberList.size()]);
        while (pos < length - 1) {

            int odd = reversedCreditCardNumber[pos] * 2;
            if (odd > 9) {
                odd -= 9;
            }

            sum += odd;

            if (pos != (length - 2)) {
                sum += reversedCreditCardNumber[pos + 1];
            }
            pos += 2;
        }

        int checkDigit = new Double(
                ((Math.floor(sum / 10) + 1) * 10 - sum) % 10).intValue();
        cardNumber += checkDigit;

        return cardNumber;

    }

    public static String[] creditCardNumber(String[] prefixList, int length,
                                              int howMany) {

        Stack<String> result = new Stack<>();
        for (int i = 0; i < howMany; i++) {
            int randomArrayIndex = (int) Math.floor(Math.random()
                    * prefixList.length);
            String creditCardNumber = prefixList[randomArrayIndex];
            result.push(completedNumber(creditCardNumber, length));
        }

        return result.toArray(new String[result.size()]);
    }


    public static boolean isValidCreditCardNumber(String creditCardNumber) {
        boolean isValid = false;

        try {
            String reversedNumber = new StringBuffer(creditCardNumber)
                    .reverse().toString();
            int mod10Count = 0;
            for (int i = 0; i < reversedNumber.length(); i++) {
                int augend = Integer.parseInt(String.valueOf(reversedNumber
                        .charAt(i)));
                if (((i + 1) % 2) == 0) {
                    String productString = String.valueOf(augend * 2);
                    augend = 0;
                    for (int j = 0; j < productString.length(); j++) {
                        augend += Integer.parseInt(String.valueOf(productString
                                .charAt(j)));
                    }
                }

                mod10Count += augend;
            }

            if ((mod10Count % 10) == 0) {
                isValid = true;
            }
        } catch (NumberFormatException e) {
        }

        return isValid;
    }

    public static String generateCreditCard(String prefix,int length) {
            String[] prefixs = {prefix};
        return creditCardNumber(prefixs, length, 1)[0];
    }

    public static String generateCard(String prefix,String length) {
        String[] prefixs = {prefix};
        return creditCardNumber(prefixs, Integer.parseInt(length), 1)[0];
    }


    public static String generateVisaCard() {
        return creditCardNumber(VISA_PREFIX_LIST, 16, 1)[0];
    }
    public static String generateMasterCard() {
        return creditCardNumber(MASTERCARD_PREFIX_LIST, 16, 1)[0];
    }
    public static String generateJcbCard() {
        return creditCardNumber(JCB_PREFIX_LIST, 16, 1)[0];
    }
    public static String generateAeCard() {
        return creditCardNumber(AE_PREFIX_LIST, 16, 1)[0];
    }
    public static String generateDiscoverCard() {
        return creditCardNumber(DISCOVER_PREFIX_LIST, 16, 1)[0];
    }
    public static String generateDinersClubCard() {
        return creditCardNumber(DINERS_CLUB_PREFIX_LIST, 16, 1)[0];
    }


    public static void main(String[] args) {
//       String visa = generateVisaCard();
//        String master = generateMasterCard();
//        String jcb = generateJcbCard();
//        String ae = generateAeCard();
//        String discover = generateDiscoverCard();
//        String dinersClub = generateDinersClubCard();
//        System.out.println("visa："+visa);
//        System.out.println("master："+master);
//        System.out.println("jcb："+jcb);
//        System.out.println("ae："+ae);
//        System.out.println("discover："+discover);
//        System.out.println("dinersClub："+dinersClub);
//
//
        String creditCard = generateCreditCard("623185" + System.currentTimeMillis(),20);
        System.out.println( creditCard + ":"
                +(isValidCreditCardNumber(creditCard) ? "valid" : "invalid"));

        System.out.println( "623185009700473421" + ":"
                +(isValidCreditCardNumber("623185009700473421") ? "valid" : "invalid"));
    }
}