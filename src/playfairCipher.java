/* © Դանիելյան Մերի Արսենի Երևան 2022
Այս լուծումը մշակվել է Հայաստանի Ազգային Պոլիտեխնիկական Համալսարանում  «Տեղեկատվության գաղտնագրային և թաքնագրային պաշտպանություն» առարկայի ընթացքում։ Ծրագրային ապահովումը վերաբերվում է Փլեյֆեյրի ալգորիթմին։ Մշակված հատվածը կիրառելու դեպքում հեղինակի անունը նշելը պարտադիր է։*/import java.util.*;

import static java.lang.Character.isWhitespace;

public class playfairCipher {
    String key;
    String text;
    char[][] matrix = new char[5][5];

    public playfairCipher(String key, String text) {
        this.key = key.toLowerCase();
        this.text = text.toLowerCase();
    }

    //կրկնվող սիմվոլների հեռացում բանալուց
    public void cleanPlayFairKey() {
        LinkedHashSet<Character> set
                = new LinkedHashSet<Character>();
        String newKey = "";
        for (int i = 0; i < key.length(); i++)
            if (!isWhitespace(key.charAt(i))) {
                set.add(key.charAt(i));
            } else {
                continue;
            }
        Iterator<Character> it = set.iterator();
        while (it.hasNext())
            newKey += (Character) it.next();
        key = newKey;
    }

    //բանալի մատրիցի ձևավորման ֆունկցիա
    public void generateCipherKey() {
        Set<Character> set = new HashSet<Character>();
        for (int i = 0; i < key.length(); i++) {
            if (key.charAt(i) == 'j')
                continue;
            else if (!isWhitespace(key.charAt(i))) {
                set.add(key.charAt(i));
            }
        }
        String tempKey = new String(key);
        for (int i = 0; i < 26; i++) {
            char ch = (char) (i + 97);
            if (ch == 'j')
                continue;
            if (!set.contains(ch))
                tempKey += ch;
        }
        for (int i = 0, index = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                matrix[i][j] = tempKey.charAt(index++);
        System.out.println("Playfair Cipher Key Matrix:");
        for (int i = 0; i < 5; i++)
            System.out.println(Arrays.toString(matrix[i]));
    }

    // տեքստի փոփոխման ֆունկցիա
    public String formatText() {
        String message = "";
        int len = text.length();
        for (int i = 0; i < len; i++) {
            // տեքստում 'j' սիմվոլի փոխարեն կգրվի 'i' սիմվոլը
            if (text.charAt(i) == 'j')
                message += 'i';
            else
                message += text.charAt(i);
        }
        // եթե երկու հաջորդական սիմվոլները նույնն են,
        // ապա նրանց մեջտեղում կավելացվի ՛z' սիմվոլը
        for (int i = 0; i < message.length(); i += 2) {
            if (message.charAt(i) == message.charAt(i + 1))
                message = message.substring(0, i + 1) + 'z'
                        + message.substring(i + 1);
        }
        //կպարզի արդյոք տեքստի երկարությունը զույգ է թե ոչ,
        // ու վերջում կավելացնի ՛z՛ սիմվոլը
        if (len % 2 == 1)
            message += 'z';
        return message;
    }

    //ֆունկցիա որը ստացված տեքստը կբաժանի զույգերի
    public String[] formPairs(String message) {
        int len = message.length();
        String[] pairs = new String[len / 2];
        for (int i = 0, cnt = 0; i < len / 2; i++)
            pairs[i] = message.substring(cnt, cnt += 2);
        return pairs;
    }

    //ֆունկցիա որը գտնում է սիմվոլի դիրքը մատրիցում
    public int[] getCharPos(char ch) {
        int[] key = new int[2];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == ch) {
                    key[0] = i;
                    key[1] = j;
                    break;
                }
            }
        }
        return key;
    }

    public String encryptMessage() {
        String text = formatText();
        String[] pairs = formPairs(text);
        String encText = "";
        for (int i = 0; i < pairs.length; i++) {
            char letter1 = pairs[i].charAt(0);
            char letter2 = pairs[i].charAt(1);
            int[] lt1 = getCharPos(letter1);
            int[] lt2 = getCharPos(letter2);
            //եթե երկու սիմվոլները նույն տողում են,
            // ապա յուր․ կփոխարինվի իր հաջորդով
            if (lt1[0] == lt2[0]) {
                lt1[1] = (lt1[1] + 1) % 5;
                lt2[1] = (lt2[1] + 1) % 5;
            }
            // եթե երկու սիմվոլները նույն սյունում են,
            // ապա յուր․ կփոխվի իրենից մի սիմվոլ ներքև գտնվողով
            else if (lt1[1] == lt2[1]) {
                lt1[0] = (lt1[0] + 1) % 5;
                lt2[0] = (lt2[0] + 1) % 5;
            }
            //եթե երկու սիմվոլները տարբեր տողերում և սյուներում են
            else {
                int temp = lt1[1];
                lt1[1] = lt2[1];
                lt2[1] = temp;
            }
            //ստանալ համապատասխան սիմվոլները բանալի մատրիցից,
            // ու վերագրել այն գաղտնագրված տեքստին
            encText = encText + matrix[lt1[0]][lt1[1]]
                    + matrix[lt2[0]][lt2[1]];
        }
        return encText;
    }

    public String decryptMessage() {
        String text = formatText();
        String[] pairs = formPairs(text);
        String decText = "";
        for (int i = 0; i < pairs.length; i++) {
            char letter1 = pairs[i].charAt(0);
            char letter2 = pairs[i].charAt(1);
            int[] lt1 = getCharPos(letter1);
            int[] lt2 = getCharPos(letter2);
            //եթե երկու սիմվոլները նույն տողում են,
            // ապա յուր․ կփոխարինվի իր նախորդով
            if (lt1[0] == lt2[0]) {
                lt1[1] = (lt1[1] - 1) % 5;
                lt2[1] = (lt2[1] - 1) % 5;
            }
            // եթե երկու սիմվոլները նույն սյունում են,
            // ապա յուր․ կփոխվի իրենից մի սիմվոլ վերև գտնվողով
            else if (lt1[1] == lt2[1]) {
                lt1[0] = (lt1[0] - 1) % 5;
                lt2[0] = (lt2[0] - 1) % 5;
            }
            //եթե երկու սիմվոլները տարբեր տողերում և սյուներում են
            else {
                int temp = lt1[1];
                lt1[1] = lt2[1];
                lt2[1] = temp;
            }
            //ստանալ համապատասխան սիմվոլները բանալի մատրիցից,
            // ու վերագրել այն գաղտնագրված տեքստին
            decText = decText + matrix[lt1[0]][lt1[1]]
                    + matrix[lt2[0]][lt2[1]];
        }
        return decText;
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("For encryption type 1 and for decryption 2");
        int answer = sc.nextInt();
        switch (answer) {
            case 1:
                System.out.println("Enter key for encryption ");
                String key = sc.nextLine();
                System.out.println("Enter text to encrypt");
                String text = sc.nextLine();
                System.out.println("Key: " + key);
                System.out.println("Text: " + text);
                playfairCipher pfc = new playfairCipher(key, text);
                pfc.cleanPlayFairKey();
                pfc.generateCipherKey();
                String encText = pfc.encryptMessage();
                System.out.println("Encrypted Text is: " + encText);

            case 2:
                System.out.println("Enter key for decryption ");
                String key1 = sc.nextLine();
                System.out.println("Enter text to decrypt");
                String text2 = sc.nextLine();
                System.out.println("Key: " + key1);
                System.out.println("Text: " + text2);
                playfairCipher pfc2 = new playfairCipher(key1, text2);
                pfc2.cleanPlayFairKey();
                pfc2.generateCipherKey();
                String decText = pfc2.decryptMessage();
                System.out.println("Decrypted Text is: " + decText);
        }
    }
}
