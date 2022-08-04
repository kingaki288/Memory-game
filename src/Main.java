
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.FileReader;



public class Main
{
    public static String get_level(char c)
    {
        if(c == 'h')
        {
            return "Hard";
        }
        else if (c == 'e')
        {
            return "Easy";
        }
        return "";
    }

    public static void main(String[] arg) throws FileNotFoundException
    {
        //1.Pobrać słowa z .txt
        FileReader fr = new FileReader("src/Words.txt");
        List<String> word_arr = new ArrayList<>();

        String word= new String();
        try
        {
            int i;
            while ( (i = fr.read()) != -1)
            {
                if (i == '\n' || i == '\r')
                {
                    if(!word.isEmpty())
                        word_arr.add(word);
                    word = "";
                    continue;
                }
                word += (char) i;
            }
        }
        catch(Exception e)
        {
            System.out.printf("try block crash\n");
            return;
        };

        //2.Zapytać o level
        //2.1 losowanie słów 4 (easy)
        //2.2 losowanie słów 8 (hard)
        System.out.printf("Hello!\nPlease choose your level:\n Press 'e' for Easy or 'h' for Hard\n");
        Scanner sc = new Scanner(System.in);
        int choice = sc.next().charAt(0);
        int nbr_of_words = 0;
        int chances = 0;
        int score = 0;
        int rows = 0;

        if (choice=='e')
        {
            nbr_of_words = 4;
            chances = 10;
            rows = 2;
        }
        else if (choice=='h')
        {
            nbr_of_words = 8;
            chances = 15;
            rows = 4;
        }

        List<String> chosen_words = new ArrayList<String>();
        for (int i = 0; i < nbr_of_words*2; i++)
        {
            chosen_words.add(new String(""));
        }

        Random rand = new Random();
        for (int k = 0 ; k < nbr_of_words; k++)
        {
            String next_word = word_arr.get(rand.nextInt(99));

            int free_sport = rand.nextInt(chosen_words.size());

            while (chosen_words.contains(next_word))
            {
                free_sport = rand.nextInt(chosen_words.size());
            }

            while(!chosen_words.get(free_sport).equals("") )
            {
                free_sport = rand.nextInt(chosen_words.size());
            }
            chosen_words.set(free_sport, next_word);

            while(!chosen_words.get(free_sport).equals(new String("")) )
            {
                free_sport = rand.nextInt(chosen_words.size());
            }
            chosen_words.set(free_sport, next_word);
        }


        //3.Wyświetlenie odpowiedniej tabeli
        System.out.printf("------------------------------------\n");
        System.out.printf("Level: " + get_level((char) choice) + "\n");
        System.out.printf("Guess chances: " + chances + "\n\n");
        System.out.printf("  1 2 3 4 \n");
        for (int i = 0; i < rows ;i++)
        {
            System.out.printf(i + 1 + " ");
            for(int j = 0; j < 4; j++)
            {
                System.out.printf( "X" + " ");
            }
            System.out.printf("\n");

        }
        System.out.printf("------------------------------------\n");
        //4.Start loopa gry
        int row1 = 0;
        int col1 = 0;
        int row2 = 0;
        int col2 = 0;
        List<Integer> guessed_slots = new ArrayList<Integer>();
        int first_word = 0;
        int second_word = 0;


        while(true)
        {
            //5.Poprosić gracza o wybór pola w tabeli
            System.out.printf("Please choose row\n");
            row1 = sc.nextInt() - 1;
            System.out.printf("Please choose column\n");
            col1 = sc.nextInt() - 1;
            first_word = row1*4 + col1;

            //6.Odkrycie 1 pola
            System.out.printf("------------------------------------\n");
            System.out.printf("Level: " + get_level((char) choice) + "\n");
            System.out.printf("Guess chances: " + chances + "\n\n");
            System.out.printf("  1 2 3 4 \n");
            for (int i = 0; i < rows ;i++)
            {
                System.out.printf(i + 1  + " ");
                for(int j = 0; j < 4; j++)
                {
                    if(guessed_slots.contains(i * 4 + j))
                    {
                        System.out.printf(chosen_words.get(i * 4 + j) + " ");
                    }
                    else if(j == col1 && i  == row1)
                    {
                        System.out.printf(chosen_words.get(i * 4 + j) + " ");
                    }
                    else
                    {
                        System.out.printf("X ");
                    }
                }
                System.out.printf("\n");
            }
            System.out.printf("------------------------------------\n");


            //7.Prośba o wytypowanie 2 pola
            System.out.printf("Please choose second row\n");
            row2 = sc.nextInt() - 1;
            System.out.printf("Please choose second column\n");
            col2 = sc.nextInt() - 1;
            second_word = row2 * 4 + col2;

            //8.Odkrycie 2 pola
            System.out.printf("------------------------------------\n");
            System.out.printf("Level: " + get_level((char) choice) + "\n");
            System.out.printf("Guess chances: " + chances + "\n\n");
            System.out.printf("  1 2 3 4 \n");
            for (int i = 0; i < rows ;i++)
            {
                System.out.printf(i + 1  + " ");
                for(int j = 0; j < 4; j++)
                {
                    if(guessed_slots.contains(i * 4 + j))
                    {
                        System.out.printf(chosen_words.get(i * 4 + j) + " ");
                        continue;
                    }
                    if((j == col1 && i  == row1))
                    {
                        System.out.printf(chosen_words.get(i * 4 + j) + " ");
                        continue;
                    }
                    if((j == col2 && i == row2))
                    {
                        System.out.printf(chosen_words.get(i * 4 + j) + " ");
                        continue;
                    }
                    System.out.printf("X ");
                }
                System.out.printf("\n");
            }
            System.out.printf("------------------------------------\n");
            //9. Logika sprawdzania obrazków
            if(first_word < 8 && second_word < 8) {
                if (chosen_words.get(first_word).equals(chosen_words.get(second_word))) {
                    score++;
                    guessed_slots.add(first_word);
                    guessed_slots.add(second_word);
                    first_word = -1;
                    second_word = -1;
                } else {
                    chances--;

                    if (chances <= 0) {
                        System.out.printf("YOU LOSE\n");
                    }
                }
            }
            else {
                chances--;

                if (chances <= 0) {
                    System.out.printf("YOU LOSE\n");
                    return;
                }
            }

            //10.Aktualizacja wyniku (rostrzygnięcie wyniku)
            if (score >= nbr_of_words)
            {
                System.out.printf("YOU WIN!\n");
                return;
            }


            System.out.printf("Board after this round:\n");
            System.out.printf("------------------------------------\n");
            System.out.printf("Level: " + get_level((char) choice) + "\n");
            System.out.printf("Guess chances: " + chances + "\n\n");
            System.out.printf("  1 2 3 4 \n");
            for (int i = 0; i < rows ;i++)
            {
                System.out.printf(i + 1  + " ");
                for(int j = 0; j < 4; j++)
                {
                    if(guessed_slots.contains(i * 4 + j))
                    {
                        System.out.printf(chosen_words.get(i * 4 + j) + " ");
                        continue;
                    }
                    System.out.printf("X ");
                }
                System.out.printf("\n");
            }
            System.out.printf("------------------------------------\n");

            //11. Powrót do punktu 5
        }

    }

}
