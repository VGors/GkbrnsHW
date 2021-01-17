package com.geekbrains.j1.lesson03;

import java.util.Random;
import java.util.Scanner;

public class Lesson3
{
    public static void main (String[] args) {
        String compWord = newWord();    //For computer's word
        Scanner readUsersWord = new Scanner(System.in);
        System.out.println("Guess the word:");
        while (true){
            String usersWord = readUsersWord.next().toLowerCase();
            if (compWord.equals(usersWord)){
                System.out.println("Congratulation! You won!");
                break;
            } else if (usersWord.equals("exit")){
                break;
            } else {
                System.out.println(compareWords(compWord, usersWord));
                System.out.println("Try again (wanna quit, type \"exit\"):");
            }
        }
        readUsersWord.close();
    }

    // Returns word from list
    public static String newWord(){
        String[] words = {"apple", "orange", "lemon", "banana", "apricot", "avocado",
                "broccoli", "carrot", "cherry", "garlic", "grape", "melon", "leak",
                "kiwi", "mango", "mushroom", "nut", "olive", "pea", "peanut", "pear",
                "pepper", "pineapple", "pumpkin", "potato"};
        Random random = new Random();
        int range = random.nextInt(words.length - 1);
        return words[range];
    }

    // Comparing words
    public static String compareWords(String original, String usWord){
        StringBuilder template = new StringBuilder();               //resulting string
        int length = Math.min(original.length(), usWord.length());  //range of checking

        for (int i = 0; i < length; i++) {
            if (original.charAt(i) == usWord.charAt(i)){
                template.append(original.charAt(i));
            } else {
                template.append("#");
            }
        }

        //Fill last "#" symbols up to 15
        for (int i = template.length(); i < 15 ; i++) {
            template.append("#");
        }
        return template.toString();
    }
}
