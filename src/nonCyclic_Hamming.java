/* © Դանիելյան Մերի Արսենի Երևան 2022
Այս լուծումը մշակվել է Հայաստանի Ազգային Պոլիտեխնիկական Համալսարանում  «Տեղեկատվության գաղտնագրային և թաքնագրային պաշտպանություն» առարկայի ընթացքում։ Ծրագրային ապահովումը վերաբերվում է Հեմինգի սխալներն ուղղող ծրագրի ալգորիթմին։ Մշակված հատվածը կիրառելու դեպքում հեղինակի անունը նշելը պարտադիր է։*/

import java.util.Scanner;

import static java.lang.Math.*;

public class nonCyclic_Hamming {
    //կոդի գեներացում, մուտքագրված թվով, նրա երկարությամբ, և ավելացվախ բիթերի քանակով
    static int[] generateCode(String str, int M, int r) {
        //նոր զանգվածի ստեղծում ավելացված բիթերի քանակով
        int[] arr = new int[r + M + 1];
        int j = 0;
        for (int i = 1; i < arr.length; i++) {
            //ցույց է տալու թվերի քանակը զանգվածում եղած
            //ceil()-ը թվի կլորացումը դեպի վերև, floor()-ը կլորացումը դեպի ներքև
            //եթե i=2^n n-ի համար երբ n պատկանում է (0,1,2,4...) տարածքին,
            // ապա arr[i] = 0 այսինքն ավելորդ բիթերը կսզկբնավորենք 0-ով
            if ((ceil(log(i) / log(2))
                    - floor(log(i) / log(2)))
                    == 0) {
                arr[i] = 0;
            } else {
                // սկզբնարժեքավորում ըստ նրանց ասքի կոդի
                arr[i] = (int) (str.charAt(j) - '0');
                j++;
            }
        }
        return arr;
    }

    //ավելորդ բիթերի հաշվում ստացած զանգվածից և զույգության համար նախատեսված բիթերի քանակից
    static int[] calculation(int[] arr, int r) {
        int length = arr.length;
        for (int i = 0; i < r; i++) {
            int x = (int) pow(2, i);
            for (int j = 1; j < length; j++) {
                //հավասարազոր է j/2^i, որը կբազմապատկվի/կհատվի 1-ով և ստացված արժեքը կստուգվի 1-ի հետ
                if (((j >> i) & 1) == 1) {
                    //եթե ստուգիչ բիթը հավասար չի տրված ինդեքսին, կկիրառենք xor օպերացիան
                    if (x != j)
                        arr[x] ^= arr[j];
                }
            }
            System.out.println("r" + x + " = "
                    + arr[x]);
        }
        return arr;
    }

    //վերծանման համար ֆունկցիա
    static int[] decode(String text) {
        int[] arr = new int[text.length() + 1];
        int j = 0;
        for (int i = 1; i < arr.length; i++) {
            if ((ceil(log(i) / log(2))
                    - floor(log(i) / log(2)))
                    == 0) {
                arr[i] = 0;
            } else {
                // սկզբնարժեքավորում ըստ նրանց ասքի կոդի
                arr[i] = (int) (text.charAt(j) - '0');
                j++;
            }
        }
        int xorOp = 0;
        //զույգության պարզում, այն բիթերի դիրքերի արժեքներով որոնցում կան 1-եր
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == 1) {
                xorOp ^= i;
            }
        }
        for (j = 1; j < arr.length; j++) {
            //սխալի ուղղում
            if (j == xorOp) {
                if (arr[xorOp] == 1) {
                    arr[xorOp] = 0;
                } else {
                    arr[xorOp] = 1;
                }
            }
        }
        return arr;
    }

    static void print(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("FOR ENCODING ENTER 1, FOR DECODING ENTER 2");
        int n = sc.nextInt();
        switch (n) {
            case 1:
                System.out.println("ENTER BINARY NUMBER TO ENCODE : ");
                String str = sc.next();
                int M = str.length();
                int r = 1;
                while (pow(2, r) < (M + r + 1)) {
                    r++;
                }
                int[] matrix = generateCode(str, M, r);
                System.out.println("GENERATED HAMMING CODE ");
                matrix = calculation(matrix, r);
                print(matrix);
                break;
            case 2:
                System.out.println("ENTER MESSAGE TO DECODE ");
                String str2 = sc.next();
                System.out.println("GENERATED AND CORRECTED ");
                int[] decoded = decode(str2);
                print(decoded);
                break;
        }
    }
}

