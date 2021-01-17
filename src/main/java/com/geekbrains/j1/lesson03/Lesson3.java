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
            String usersWord = readUsersWord.next().toLowerCase();  //reducing errors
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
        int range = random.nextInt(24);
        return words[range];
    }

    // Comparing words
    public static String compareWords(String original, String usWord){
        String template = "";   //resulting string
        int length;             //range of checking
        if (original.length() <= usWord.length()){
            length = original.length();
        }
        else {
            length = usWord.length();
        }
        for (int i = 0; i < length; i++) {
            if (original.charAt(i) == usWord.charAt(i)){
                template += original.charAt(i);
            } else {
                template += "#";
            }
        }

        //Fill last "#" symbols up to 15
        for (int i = template.length(); i < 15 ; i++) {
            template += "#";
        }
        return template;
    }

}
