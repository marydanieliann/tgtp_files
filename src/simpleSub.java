/* © Դանիելյան Մերի Արսենի Երևան 2022
Այս լուծումը մշակվել է Հայաստանի Ազգային Պոլիտեխնիկական Համալսարանում  «Տեղեկատվության գաղտնագրային և թաքնագրային պաշտպանություն» առարկայի ընթացքում։ Ծրագրային ապահովումը վերաբերվում է Պարզ Փոխարինման ալգորիթմին։ Մշակված հատվածը կիրառելու դեպքում հեղինակի անունը նշելը պարտադիր է։*/

import java.util.LinkedHashSet;
import java.util.Scanner;

import static java.lang.Character.isWhitespace;


public class simpleSub {
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


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter key for encryption - ");
        String key = sc.nextLine();
        System.out.println("\nEnter text to encrypt - ");
        String text = sc.nextLine();
        String encrypted = coding(key, text);
        System.out.println("Encrypted text is - " + encrypted);

    }

}