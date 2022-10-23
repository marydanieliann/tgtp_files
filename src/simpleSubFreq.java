
/* © Դանիելյան Մերի Արսենի Երևան 2022
Այս լուծումը մշակվել է Հայաստանի Ազգային Պոլիտեխնիկական Համալսարանում  «Տեղեկատվության գաղտնագրային և թաքնագրային պաշտպանություն» առարկայի ընթացքում։ Ծրագրային ապահովումը վերաբերվում է Լեզվի վիճակագրությանը։ Մշակված հատվածը կիրառելու դեպքում հեղինակի անունը նշելը պարտադիր է։*/

import java.util.*;

import static java.lang.Character.isWhitespace;

public class simpleSubFreq {
    static String alphabet = "abcdefghijklmnopqrstuvwxyz";
    static StringBuilder alpBuilder = new StringBuilder();

    public static String coding(String key, String value) {
        StringBuilder encrypted = new StringBuilder();
        StringBuilder originalAlph = new StringBuilder(alphabet);
        LinkedHashSet<Character> set = new LinkedHashSet<>();
        String message = key.toLowerCase();
        String text = value.toLowerCase();
        //բանալու ֆիլտրում, հանվում են կրկվող տառերը մուտքագրված տեքստից, թողնվելով առաջին հանդիպածը
        //եթե մուտքագրված տեքստում կա բացատ, ուղղակի առաջ անցնել
        //հակառակ դեպքում, սեթում ավելացնել տառը
        for (int i = 0; i < message.length(); i++) {
            if (isWhitespace(message.charAt(i))) {
                continue;
            } else {
                set.add(message.charAt(i));

            }
        }

        for (char i : set) {
            for (int j = 0; j < message.length(); j++) {
                if (i == originalAlph.charAt(j)) {
                    //այբուբենից հեռացնել նախորդ քայլով ստացված կրկնվող տառերը
                    originalAlph.deleteCharAt(j);
                    break;
                }
            }
        }
        //ստեղծվում է նոր այբուբեն, ու ավելացվում է սեթում
        for (int i = 0; i < originalAlph.length(); i++) {
            set.add(originalAlph.charAt(i));
        }

        //այբուբենը փոխանցվում է սեթից տողային տիպի փոփոխականի մեջ
        for (char i : set) {
            alpBuilder.append(i);
        }


        System.out.println();
        for (int i = 0; i < text.length(); i++) {
            for (int j = 0; j < alphabet.length(); j++) {
                //եթե տեքստի տվյալ դիրքում գտնվող սիմվոլը բացատ է, գաղտնագրված տեքստում ավելացնել բացատ
                if (text.charAt(i) == ' ') {
                    encrypted.append(" ");
                    break;
                }
                //եթե տեքստում ունեցած սիմվոլը նույնն է ինչ իրական այբուբենում եղած սիմվոլը,
                //գաղտնագրված տեքստում ավելացնել ձևավորված այբուբենում համապատասխան դիրքում գտնվող սիմվոլը
                else if (text.charAt(i) == alphabet.charAt(j)) {
                    encrypted.append(alpBuilder.charAt(j));
                    break;
                }
            }
        }
       /* System.out.println("Original alphabet is - " + alphabet);
        System.out.println("\nNew alphabet is- " + alpBuilder.toString());*/
        return encrypted.toString();
    }

    //լեզվի վիճակագրություն
    public static Map<Character, Float> freqForEncrypted(String mess) {
        Map<Character, Float> map = new HashMap<>();
        String message = mess.toLowerCase();
        int[] freq1 = new int[message.length()];
        float[] f = new float[message.length()];
        int amount = message.length();
        char[] str = message.toCharArray();
        for (int i = 0; i < message.length(); i++) {
            //պարզել սիմվոլը այբբենական է թե ոչ, որպեսզի կարողանանք ստանալ նախադասության մեջ եղած սիմվոլների քանակը
            if (!Character.isAlphabetic(message.charAt(i))) {
                amount--;
            }
        }

        //ամեն մի սիմվոլի կրկնվելու քանակի պարզում
        for (int i = 0; i < message.length(); i++) {
            //նախնական ամեն մեկի կրկնվելու քանակը նշանակվում է 1
            freq1[i] = 1;
            for (int j = i + 1; j < message.length(); j++) {
                //եթե նախադասության իրար հաջորդող,
                //նախադասության որևէ դիրքում գտնվող սիմվոլները նույնն են,
                //ապա հաճախությունն ավելացնել մեկով,
                // իսկ նախասության մեջ երկրորդ անգամ կրկնվող տեղում ավելացնել 0
                if (str[i] == str[j]) {
                    freq1[i]++;
                    str[j] = '0';
                }
            }
        }

        for (int i = 0; i < freq1.length; i++) {
            if (Character.isAlphabetic(str[i])) {
                f[i] = (float) freq1[i] * 100 / amount;
                map.put(str[i], f[i]);
            }
        }
        printMap(sortByValue(map));

        return map;
    }

    //նվազման կարգով սորտավորում
    private static Map<Character, Float> sortByValue(Map<Character, Float> unsortedMap) {
        Map<Character, Float> sortedMap = new LinkedHashMap<>();
        unsortedMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

        return sortedMap;
    }

    public static <K, V> void printMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println(entry.getKey()
                    + " - " + entry.getValue() + "%");
        }
    }

    //վերծանման համար նախատեսված ֆունկցիա
    public static String format(String encrypted, String compare) {
        String compareText = compare.toLowerCase();
        StringBuilder decrypted = new StringBuilder();
        StringBuilder newAlpForComparing = new StringBuilder();
        StringBuilder newAlpForEncrypted = new StringBuilder();
        Map<Character, Float> map = new TreeMap<>();
        System.out.println("Frequency for encrypted text - ");
        map = sortByValue(freqForEncrypted(encrypted));
        System.out.println("Frequency for compared text - ");
        Map<Character, Float> map1 = new TreeMap<>();
        map1 = sortByValue(freqForEncrypted(compareText));
        //ստեղծենք նոր այբուբեն համեմատման ենթակա տեքստից ստացված սիմվոլներով
        for (Map.Entry<Character, Float> entry : map1.entrySet()) {
            newAlpForComparing.append(entry.getKey());
        }
        for (int j = 0; j < 26; j++) {
            if (newAlpForComparing.charAt(j) == alphabet.charAt(j)) {
                continue;
            } else {
                newAlpForComparing.append(alphabet.charAt(j));
            }
        }
        //ստեղծենք նոր այբուբեն գաղտնագրված տեքստից ստացված սիմվոլներով
        for (Map.Entry<Character, Float> entry : map.entrySet()) {
            newAlpForEncrypted.append(entry.getKey());
        }
        for (int j = 0; j < 26; j++) {
            if (newAlpForEncrypted.charAt(j) == alphabet.charAt(j)) {
                continue;
            } else {
                newAlpForEncrypted.append(alphabet.charAt(j));
            }
        }
        //վերծանում, գտնելով ստացված այբուբենում համապատասխան դիրքում գտնվող սիմվոլի հետ փոխարինումով
        for (int i = 0; i < encrypted.length(); i++) {
            for (int j = 0; j < newAlpForComparing.length(); j++) {
                if (encrypted.charAt(i) == ' ') {
                    decrypted.append(" ");
                    break;
                } else if (encrypted.charAt(i) == newAlpForEncrypted.charAt(j)) {
                    decrypted.append(newAlpForComparing.charAt(j));
                    break;
                }
            }
        }

        return decrypted.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter key for encryption ");
        String key = sc.nextLine();
        System.out.println("\nEnter text to encrypt ");
        String text = sc.nextLine();
        String encrypted = coding(key, text);
        System.out.println("Encrypted text is - " + encrypted);
        // freqForEncrypted(encrypted);
        System.out.println("\nEnter text to compare ");
        String compare = sc.nextLine();
        // freqForEncrypted(compare);
        System.out.println("\nNew text can be - " + format(encrypted, compare));
    }

}
