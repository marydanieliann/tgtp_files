
/* © Դանիելյան Մերի Արսենի Երևան 2022
Այս լուծումը մշակվել է Հայաստանի Ազգային Պոլիտեխնիկական Համալսարանում «Տեղեկատվության գաղտնագրային և թաքնագրային պաշտպանություն» առարկայի ընթացքում։
 Ծրագրային ապահովումը վերաբերվում է սխալներն ուղղող կոդի ալգորիթմին (մատրիցով ստուգման դեպքում)։ Մշակված հատվածը կիրառելու դեպքում հեղինակի անունը նշելը պարտադիր է։*/


import java.util.Scanner;

import static java.lang.Math.sqrt;

public class ecCodeMatrix {
    //ֆունկցիա, որը կստուգի ներմուծված զանգվածի չափսը,
    // և ըստ դրա կստեղծի երկչափ զանգված, որում կկատարավի սխալների ուղղման գործընթացը
    static int[][] makeMatrix(int[] number, int l) {
        int[][] arr = {{0}, {1}};
        //ներմուծված զանգվածի երկարությունը պետք է լինի կենտ և որևէ թվի քառակուսի
        if (sqrt(l) % 2 == 1) {
            int length = (int) sqrt(l);
            arr = new int[length + 1][length + 1];
            int lengthArr = 0;
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length; j++) {
                    arr[i][j] = number[lengthArr];
                    lengthArr++;
                }
            }
        }
        return arr;
    }

    //սխալների ուղղման համար ֆունկցիա
    static int[][] generateCode(int[][] arr) {
        //զույգությունն ապահովելու համար նախատեսված փոփոխական
        int xorOP;
        int l = arr.length;
        //տողերի համար զույգության ճշգրտում xor օպերացիայի միջոցով
        for (int i = 0; i < l - 1; i++) {
            xorOP = 0;
            for (int j = 0; j < l - 1; j++) {
                xorOP ^= arr[i][j];
            }
            arr[i][l - 1] = xorOP;
        }

        //սյուների համար զույգության ճշգրտում xor օպերացիայի միջոցով
        for (int i = 0; i < l - 1; i++) {
            xorOP = 0;
            for (int j = 0; j < l - 1; j++) {
                xorOP ^= arr[j][i];
            }
            arr[l - 1][i] = xorOP;
        }
        return arr;
    }

    //վերծանման համար նախատեսված ֆունկցիա
    static int[][] decode(int[][] arr1) {
        //զույգությունը ճշգրտող փոփոխական
        int xorOP = 0;
        int l = arr1.length;
        //label-ի հայտատրարում ցիկլի սկզբից առաջ, որը պետք է ավարտենք պայմանի կատարման դեպքում
        outer:
        for (int i = 0; i < l; i++) {
            //փոփոխականի վերագրում առաջին սյան թվերին, և զույգության ճշգրտում
            xorOP = arr1[i][0];
            // սխալի պարզում տողերում և սյուներում, առանց ստուգելու ստուգիչ բիթի մեջ սխալի առկայությունը
            if (i == l - 1) {
                for (int j = 1; j < l - 1; j++) {
                    xorOP ^= arr1[i][j];
                }
            }
            //սխալի պարզում նաև ստուգիչ բիթում
            else {
                for (int j = 1; j < l; j++) {
                    xorOP ^= arr1[i][j];
                }
            }
            if (i == l - 1) {
                if (xorOP == 1) {
                    xorOP = 0;
                } else {
                    xorOP = 1;
                }
            }
            if (xorOP == 1) {
                for (int k = 0; k < l; k++) {
                    xorOP = arr1[0][k];
                    if (k == l - 1) {
                        for (int t = 1; t < l - 1; t++) {
                            xorOP ^= arr1[t][k];
                        }
                    } else {
                        for (int t = 1; t < l; t++) {
                            xorOP ^= arr1[t][k];
                        }
                    }
                    if (k == l - 1) {
                        if (xorOP == 1) {
                            xorOP = 0;
                        } else {
                            xorOP = 1;
                        }
                    }
                    //պայմանի ստուգում, սխալ գրված ինդեքսների հայտանբերմամբ, և ուղղում
                    if (xorOP == 1) {
                        System.out.println("[" + i + "][" + k + "]\n\n");
                        if (arr1[i][k] == 1) {
                            arr1[i][k] = 0;
                        } else {
                            arr1[i][k] = 1;
                        }
                        break outer;
                    }
                }
            }
        }
        return arr1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("FOR ENCODING ENTER 1, FOR DECODING 2");
        int enter = sc.nextInt();
        switch (enter) {
            case 1:
                System.out.println("HOW MANY BITS HAS THE NUMBER : ");
                int n = sc.nextInt();
                int[] arr = new int[n];
                System.out.println("INPUT NUMBER IN BINARY : ");
                for (int i = 0; i < n; i++) {
                    System.out.println("FOR INDEX " + i);
                    arr[i] = sc.nextInt();
                }
                int[][] matrix = generateCode(makeMatrix(arr, n));
                for (int i = 0; i < matrix.length; i++) {
                    for (int j = 0; j < matrix.length; j++) {
                        System.out.print(matrix[i][j] + " ");
                    }
                    System.out.println();
                }
                break;
            case 2:
                System.out.println("HOW MANY BITS HAS THE NUMBER AFTER ECC : ");
                int n1 = sc.nextInt();
                int l = (int) sqrt(n1);
                int[][] arr1 = new int[l][l];
                System.out.println("INPUT NUMBER IN BINARY : ");
                for (int i = 0; i < l; i++) {
                    for (int j = 0; j < l; j++) {
                        System.out.println("FOR " + i + " LINE AND " + j + " COLUMN");
                        arr1[i][j] = sc.nextInt();
                    }
                }
                int[][] decoded = decode(arr1);
                for (int i = 0; i < decoded.length; i++) {
                    for (int j = 0; j < decoded.length; j++) {
                        System.out.print(decoded[i][j] + " ");
                    }
                    System.out.println();
                }
                break;
        }
    }
}
