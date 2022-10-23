/* © Դանիելյան Մերի Արսենի Երևան 2022
Այս լուծումը մշակվել է Հայաստանի Ազգային Պոլիտեխնիկական Համալսարանում  «Տեղեկատվության գաղտնագրային և թաքնագրային պաշտպանություն» առարկայի ընթացքում։ Ծրագրային ապահովումը վերաբերվում է Կեսարի ալգորիթմին։ Մշակված հատվածը կիրառելու դեպքում հեղինակի անունը նշելը պարտադիր է։*/

import java.util.Scanner;

public class caesarCipher {
    int originalAlph;
    int newAlph;
    char ch1;

    //գաղտնագրման համար նախատեսված ֆունկցիա
    public String encrypt(String message, int d) {
        StringBuilder encrypted = new StringBuilder();
        String low = message.toLowerCase();
        char[] matrix = low.toCharArray();
        for (char ch : matrix) {
            if (ch != ' ') {
                //գտնում ենք տվյալ սիմվոլի դիրքը,
                // դրա համար ստացված սիմվոլից հանում ենք 'a' սիմվոլի ASCII կոդը
                originalAlph = ch - 'a';
                //գումարում ենք թիվը, որով պետք է կատարվի տեղափոխություն
                //այբուբենի դիապազոնում մնալու համար օգտագործում ենք մոդուլը
                newAlph = (originalAlph + d) % 26;
                //ստանում ենք նոր սիմվոլը,
                // ավելացնելով նրան 'a' սիմվոլի ASCII կոդը և ավելացնելով գաղտնագրված տողի մեջ
                ch1 = (char) ('a' + newAlph);
                encrypted.append(ch1);
            } else {
                encrypted.append(ch);
            }
        }
        return encrypted.toString();
    }

    //վերծանման համար նախատեսված ֆունկցիա
    public String decrypt(String message, int d) {
        return encrypt(message, 26 - d);
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        caesarCipher cs = new caesarCipher();
        System.out.println("If you want to encrypt type 1, if decrypt 2 and for brute force enter 3");
        int question = Integer.parseInt(in.nextLine());
        System.out.println();
        switch (question) {
            case 1:
                System.out.println("Enter the text to encrypt");
                String text = in.nextLine();
                System.out.println();
                System.out.println("Enter the number for shifting");
                int d = Integer.parseInt(in.nextLine());
                System.out.println();
                if (d >= 0) {
                    System.out.println("Encrypted text " + cs.encrypt(text, d));

                } else if (d > 26) {
                    d = d - 26;
                    System.out.println("Encrypted text " + cs.encrypt(text, d));

                } else {
                    System.out.println("shifting key was entered wrong");
                }
                break;

            case 2:
                System.out.println("Enter the text to decrypt");
                String text1 = in.nextLine();
                System.out.println();
                System.out.println("Enter the number for shifting");
                int d1 = Integer.parseInt(in.nextLine());
                System.out.println();
                if (d1 >= 0) {
                    System.out.println("Decrypted text " + cs.decrypt(text1, d1));

                } else if (d1 > 26) {
                    d1 = d1 - 26;
                    System.out.println("Decrypted text " + cs.decrypt(text1, d1));

                } else {
                    System.out.println("shifting key was entered wrong");
                }
                break;

            case 3:
                int d2;
                System.out.println("For brute force attempt, enter text");
                String text2 = in.nextLine();
                System.out.println();
                for (d2 = 1; d2 < 26; d2++) {
                    System.out.println("Decrypted text can be : " + cs.encrypt(text2, d2));
                }
        }
    }
}