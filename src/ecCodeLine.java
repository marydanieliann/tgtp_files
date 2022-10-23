/* © Դանիելյան Մերի Արսենի Երևան 2022
Այս լուծումը մշակվել է Հայաստանի Ազգային Պոլիտեխնիկական Համալսարանում  «Տեղեկատվության գաղտնագրային և թաքնագրային պաշտպանություն» առարկայի ընթացքում։
Ծրագրային ապահովումը վերաբերվում է սխալներն ուղղող կոդի ալգորիթմին (մեկ տողով ստուգման դեպքում)։ Մշակված հատվածը կիրառելու դեպքում հեղինակի անունը նշելը պարտադիր է։*/


import java.util.LinkedList;
import java.util.Scanner;

public class ecCodeLine {
    public static LinkedList<Integer> makeMatrix(LinkedList<Integer> list) {
        if (list.size() % 3 != 0) {
            int size = list.size() / 3;
            for (int i = 0; i < size; i++) {
                list.addFirst(0);
            }
        }
        return list;
    }

    public static void encode(LinkedList<Integer> list) {
        int[] xorOP = new int[list.size()];
        int index = 0;
        for (int i = 0; i < list.size(); i += 3) {
            xorOP[index] = list.get(i) ^ list.get(i + 1) ^ list.get(i + 2);
            index++;
        }
        index = 0;
        for (int i = 0; i < list.size(); i += 3) {
            list.add(i + 3, xorOP[index]);
            i++;
            index++;
        }
        for (Integer i : list) {
            System.out.print(i);
        }
    }

    public static void decode(LinkedList<Integer> list) {
        if (list.size() % 4 == 0) {
            int[] xorOP = new int[list.size()];
            int index = 0;
            for (int i = 0; i < list.size(); i += 4) {
                xorOP[index] = list.get(i) ^ list.get(i + 1) ^ list.get(i + 2);
                index++;
            }
            index = 0;
            for (int i = 0; i < list.size(); i += 4) {
                if (list.get(i + 3) == xorOP[index]) {
                    index++;
                    continue;
                } else {
                    list.set(i + 3, xorOP[index]);
                    index++;
                }
            }
            for (Integer i : list) {
                System.out.print(i);
            }

        } else {
            System.out.println("WRONG AMOUNT OF BITS WAS ENTERED");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("FOR DECODING ENTER 2, FOR ENCODING 1");
        int number = sc.nextInt();
        switch (number) {

            case 1:
                System.out.println("HOW MANY BITS HAS THE NUMBER : ");
                int n = sc.nextInt();
                LinkedList<Integer> list = new LinkedList<>();
                for (int i = 0; i < n; i++) {
                    System.out.println("INPUT NUMBER FOR INDEX " + i);
                    list.add(i, sc.nextInt());
                }
                encode(makeMatrix(list));
            case 2:
                System.out.println("HOW MANY BITS HAS THE NUMBER AFTER ECC LINE (IT MUST CONTAIN 4, 8, 12 OR ETC: ");
                int n2 = sc.nextInt();
                LinkedList<Integer> list2 = new LinkedList<>();
                for (int i = 0; i < n2; i++) {
                    System.out.println("INPUT NUMBER FOR INDEX " + i);
                    list2.add(i, sc.nextInt());
                }
                decode(list2);
        }
    }
}

