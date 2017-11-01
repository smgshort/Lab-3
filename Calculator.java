// Author: Shaina Greer-Short
// Date: 10.30.17
// File: Calculator_Greer-Short_Shaina


// Class Header and Scanner
import java.util.Scanner;
import java.util.Arrays;
public class Calculator
{
   
   // the main routine
   public static void main (String[] args) 
   {
   
      Scanner input = new Scanner(System.in);
      equation(input);
   }
   
   //recieves the equation as an array input and calls other methods
   public static void equation (Scanner input){
      System.out.println("Would you like to enter an equation (enter yes if you would like to any other anser will be taken as no)?");
      String more = input.nextLine();
      while(more.equals("yes")){
         System.out.println("Please enter your equation (do not input any spaces).");
         char[] equation = input.next().toCharArray();
         boolean correct = check(equation);
         if(correct){
            equation = equals(equation);
            equation = parenthesis(equation, -1);
            equation  = calculate(equation);
            if(equation.length > 0){
               System.out.print("The equation is equal to ");
               System.out.print(equation);
               System.out.println();
            }
         }else{
            System.out.println("This equation is invalid, it contains incorrect characters");
         }
         System.out.println("Would you like to enter an equation (enter yes if you would like to any other anser will be taken as no)?");
         more = input.next();
      }
   }
   
   //checks that all values entered are possible
   public static boolean check(char[] equation){
      for (int i = 0; i < equation.length; i++){
         if(equation[i] == '1' || equation[i] == '2' || equation[i] == '3'|| equation[i] == '4'|| equation[i] == '5'|| equation[i] == '6'|| equation[i] == '7'||
          equation[i] == '8'|| equation[i] == '9'|| equation[i] == '0'|| equation[i] == '+'|| equation[i] == '-'|| equation[i] == '='|| equation[i] == '*'|| 
          equation[i] == '/'|| equation[i] == '^'|| equation[i] == '('|| equation[i] == ')'){
         }else{
            return false;
         }
      }
      return true;
   }
   
   //removes equals sign at the end if there is one
   public static char[] equals(char[] equation){
      if(equation[equation.length-1] == '='){
         return Arrays.copyOfRange(equation, 0, equation.length-1);
      }
      return equation;
   }
   
   //calculates sub formulas contained in parenthesis
   public static char[] parenthesis(char[] equation, int place){
      int place2 = -1;
      for (int i = place +1 ; i < equation.length; i++){
         if(equation[i] == '('){
            place = i;
         }else if(equation[i] == ')' && place != -1 && i != place+1){
            place2 = i;
            char[] value = calculate(Arrays.copyOfRange(equation, place+1, i));
            char[] hold = new char[equation.length-i+place-1+value.length];
            int where = 1;
            for(int j = 0; j < hold.length; j++){
               if(j < place){
                  hold[j] = equation[j];
               }else if (place-1+value.length >= j){
                  hold[j] = value[j-place];
               }else{
                  hold[j] = equation[i+where];
                  where++;
               }
            }
            i = -1;
            equation = hold;
         }else if(equation[i] == ')' && place != -1 && i == place+1){ 
             place2 = 1; 
             equation[place] = 0;
             equation[i] = 0;
         }else if(equation[i] == ')' && place == -1){
            System.out.println("This equation is invalid, it contains incorrect characters");
            char[] blank = new char[0];
            return blank;
         }else{
         }
      }
      //checks for extra parenthesis
      if(place2 == -1 && place > -1){
         System.out.println("This equation is invalid, it contains incorrect characters");
         char[] blank = new char[0];
         return blank;
      }
      return equation;
   }
   
   //calculates the equation based on the order of operations
   public static char[] calculate(char[] equation){
      //determine the value of ^ numbers
      for(int i = 0 ; i < equation.length; i++){
         if(equation[i] == '^'){
            int amount1 = 1;
            double number1 = 0;  //find number before ^
            int points = 0;
            int digits = 1;
            while(i-amount1 >= 0 && (equation[i-amount1] == '1' || equation[i-amount1] == '2' || equation[i-amount1] == '3' || equation[i-amount1] == '4' || 
            equation[i-amount1] == '5'|| equation[i-amount1] == '6' || equation[i-amount1] == '7' || equation[i-amount1] == '8' || equation[i-amount1] == '9' || 
            equation[i-amount1] == '0'|| equation[i-amount1] == '.')){
               if(equation[i-amount1] == '.' && points ==0){
                  number1 = number1/(Math.pow(10, amount1-1));
                  amount1++;
                  points++;
                  digits = 1;
               }else if(equation[i-amount1] == '.' && points !=0){
                  System.out.println("This equation is invalid, it does not have the characters in a logical order.");
                  char[] blank = new char[0];
                  return blank;
               }else{
                  number1 = number1 + Character.getNumericValue(equation[i-amount1])* (int)Math.pow(10, digits-1);
                  amount1++;
                  digits++;
               }
            }
            //determines if the number is negative
            if(i-amount1 >= 0 && equation[i-amount1] == '-' &&(i-amount1-1 < 0 || (equation[i-amount1-1] != '1' && equation[i-amount1-1] != '2' && equation[i-amount1-1] != '3' &&
                equation[i-amount1-1] != '4' && equation[i-amount1-1] != '5'&& equation[i-amount1-1] != '6' && equation[i-amount1-1] != '7' && equation[i-amount1-1] != '8' &&
                 equation[i-amount1-1] != '9' && equation[i-amount1-1] != '0'))){
               number1 = -number1;
               amount1++;
            }
            int amount2 = 1;
            double number2 = 0;      //finds number after ^
            boolean negative = false;
            boolean digit = false;
            int digits2 = 1;
            while(i+amount2 < equation.length && (equation[i+amount2] == '1' || equation[i+amount2] == '2' || equation[i+amount2] == '3' || equation[i+amount2] == '4' || 
            equation[i+amount2] == '5'|| equation[i+amount2] == '6' || equation[i+amount2] == '7' || equation[i+amount2] == '8' || equation[i+amount2] == '9' || 
            equation[i+amount2] == '0' || equation[i+amount2] == '.'|| equation[i+amount2] == '-')){
               if(equation[i+amount2] == '-' && amount2 == 1){//determines if number is negative
                  negative = true;
                  amount2++;
               }else if (equation[i+amount2] == '-' && amount2 != 1){ //determines if - is in wrong place
                  System.out.println("This equation is invalid, it does not have the characters in a logical order.");
                  char[] blank = new char[0];
                  return blank;
               }else if(equation[i+amount2] == '.'){
                  digit = true;
                  amount2++;
               }else if (digit){
                  number2 = (Character.getNumericValue(equation[i+amount2])/(int)Math.pow(10, digits2))+number2;
                  digits2++;
                  amount2++;
               }else{
                  number2 = Character.getNumericValue(equation[i+amount2])+number2*10;
                  amount2++;
               }
            }
            //makes the negtive if it is supposed to be
            if(negative){
               number2 = -number2;
            }
            if(amount2 == 1||amount1 == 1){
               System.out.println("This equation is invalid, it does not have the characters in a logical order.");
               char[] blank = new char[0];
               return blank;
            }
            double valued = Math.pow(number1, number2);
            char[] value = ("" + valued).toCharArray();
            char[] hold = new char[equation.length-amount1-amount2+1+value.length];
            int where = 0;
            int where2 = 0;   //recreates array with new values
            for(int j = 0; j < hold.length; j++){
               if(j <= i-amount1){
                  hold[j] = equation[j];
               }else if (i-amount1+value.length >= j){
                  hold[j] = value[where];
                  where++;
               }else{
                  hold[j] = equation[i+amount2+where2];
                  where2++;
               }
            }
            equation = hold;
            i = -1; 
         } 
      }
      
      //determine the value of * and / numbers
      for(int i = 0 ; i < equation.length; i++){
         if(equation[i] == '*' || equation[i] == '/'){
            int amount1 = 1;
            double number1 = 0;  //find number before * or /
            int points = 0;
            int digits = 1;
            while(i-amount1 >= 0 && (equation[i-amount1] == '1' || equation[i-amount1] == '2' || equation[i-amount1] == '3' || equation[i-amount1] == '4' || 
            equation[i-amount1] == '5'|| equation[i-amount1] == '6' || equation[i-amount1] == '7' || equation[i-amount1] == '8' || equation[i-amount1] == '9' || 
            equation[i-amount1] == '0' || equation[i-amount1] == '.')){
               if(equation[i-amount1] == '.' && points ==0){
                  number1 = number1/(Math.pow(10, amount1-1));
                  amount1++;
                  points++;
                  digits = 1;
               }else if(equation[i-amount1] == '.' && points !=0){
                  System.out.println("This equation is invalid, it does not have the characters in a logical order.");
                  char[] blank = new char[0];
                  return blank;
               }else{
                  number1 = number1 + Character.getNumericValue(equation[i-amount1])* (int)Math.pow(10, digits-1);
                  amount1++;
                  digits++;
               }
            }
            //determines if the number is negative
            if(i-amount1 >= 0 && equation[i-amount1] == '-' &&(i-amount1-1 < 0 || (equation[i-amount1-1] != '1' && equation[i-amount1-1] != '2' && equation[i-amount1-1] != '3' &&
                equation[i-amount1-1] != '4' && equation[i-amount1-1] != '5'&& equation[i-amount1-1] != '6' && equation[i-amount1-1] != '7' && equation[i-amount1-1] != '8' &&
                 equation[i-amount1-1] != '9' && equation[i-amount1-1] != '0'))){
               number1 = -number1;
               amount1++;
            }
            int amount2 = 1;
            double number2 = 0;      //finds number after * or /
            boolean negative = false;
            boolean digit = false;
            int digits2 = 1;
            while(i+amount2 < equation.length && (equation[i+amount2] == '1' || equation[i+amount2] == '2' || equation[i+amount2] == '3' || equation[i+amount2] == '4' || 
            equation[i+amount2] == '5'|| equation[i+amount2] == '6' || equation[i+amount2] == '7' || equation[i+amount2] == '8' || equation[i+amount2] == '9' || 
            equation[i+amount2] == '0' || equation[i+amount2] == '.'|| equation[i+amount2] == '-')){
               if(equation[i+amount2] == '-' && amount2 == 1){//determines if number is negative
                  negative = true;
                  amount2++;
               }else if (equation[i+amount2] == '-' && amount2 != 1){ //determines if - is in wrong place
                  System.out.println("This equation is invalid, it does not have the characters in a logical order.");
                  char[] blank = new char[0];
                  return blank;
               }else if(equation[i+amount2] == '.'){
                  digit = true;
                  amount2++;
               }else if (digit){
                  number2 = (Character.getNumericValue(equation[i+amount2])/(int)Math.pow(10, digits2))+number2;
                  digits2++;
                  amount2++;
               }else{
                  number2 = Character.getNumericValue(equation[i+amount2])+number2*10;
                  amount2++;
               }
            }
            //makes it negative if it is suppoesed to be
            if(negative){
               number2 = -number2;
            }
            if(amount2 == 1 ||amount1 == 1 || number2 == 0){
               System.out.println("This equation is invalid, it does not have the characters in a logical order.");
               char[] blank = new char[0];
               return blank;
            }
            double valued;
            if (equation[i] == '*'){
               valued = number1*number2;
            }else{
               valued = number1/number2;
            }
            char[] value = ("" + valued).toCharArray();
            char[] hold = new char[equation.length-amount1-amount2+1+value.length];
            int where = 0;
            int where2 = 0;   //recreates array with new values
            for(int j = 0; j < hold.length; j++){
               if(j <= i-amount1){
                  hold[j] = equation[j];
               }else if (i-amount1+value.length >= j){
                  hold[j] = value[where];
                  where++;
               }else{
                  hold[j] = equation[i+amount2+where2];
                  where2++;
               }
            }
            equation = hold;
            i = -1;
         } 
      }
      
      //determine the value of + and - numbers
      for(int i = 0 ; i < equation.length; i++){
         if(equation[i] == '+' || (equation[i] == '-' && i != 0)){
         
            int amount1 = 1;
            double number1 = 0;  //find number before * or /
            int points = 0;
            int digits = 1;
            while(i-amount1 >= 0 && (equation[i-amount1] == '1' || equation[i-amount1] == '2' || equation[i-amount1] == '3' || equation[i-amount1] == '4' || 
            equation[i-amount1] == '5'|| equation[i-amount1] == '6' || equation[i-amount1] == '7' || equation[i-amount1] == '8' || equation[i-amount1] == '9' || 
            equation[i-amount1] == '0' || equation[i-amount1] == '.')){
               if(equation[i-amount1] == '.' && points ==0){
                  number1 = number1/(Math.pow(10, amount1-1));
                  amount1++;
                  points++;
                  digits = 1;
               }else if(equation[i-amount1] == '.' && points !=0){
                  System.out.println("This equation is invalid, it does not have the characters in a logical order.");
                  char[] blank = new char[0];
                  return blank;
               }else{
                  number1 = number1 + Character.getNumericValue(equation[i-amount1])* (int)Math.pow(10, digits-1);
                  amount1++;
                  digits++;
               }
            }
            //determines if the number is negative
            if(i-amount1 >= 0 && equation[i-amount1] == '-' &&(i-amount1-1 < 0 || (equation[i-amount1-1] != '1' && equation[i-amount1-1] != '2' && equation[i-amount1-1] != '3' &&
                equation[i-amount1-1] != '4' && equation[i-amount1-1] != '5'&& equation[i-amount1-1] != '6' && equation[i-amount1-1] != '7' && equation[i-amount1-1] != '8' &&
                 equation[i-amount1-1] != '9' && equation[i-amount1-1] != '0'))){
               number1 = -number1;
               amount1++;
            }
            
            int amount2 = 1;
            double number2 = 0;      //finds number after * or /
            boolean negative = false;
            boolean digit = false;
            int digits2 = 1;
            while(i+amount2 < equation.length && (equation[i+amount2] == '1' || equation[i+amount2] == '2' || equation[i+amount2] == '3' || equation[i+amount2] == '4' || 
            equation[i+amount2] == '5'|| equation[i+amount2] == '6' || equation[i+amount2] == '7' || equation[i+amount2] == '8' || equation[i+amount2] == '9' || 
            equation[i+amount2] == '0' || equation[i+amount2] == '.'|| equation[i+amount2] == '-')){
               if(equation[i+amount2] == '-' && amount2 == 1){//determines if number is negative
                  negative = true;
                  amount2++;
               }else if (equation[i+amount2] == '-' && amount2 != 1){ //determines if - is in wrong place
                  System.out.println("This equation is invalid, it does not have the characters in a logical order.");
                  char[] blank = new char[0];
                  return blank;
               }else if(equation[i+amount2] == '.'){
                  digit = true;
                  amount2++;
               }else if (digit){
                  number2 = (Character.getNumericValue(equation[i+amount2])/(int)Math.pow(10, digits2))+number2;
                  digits2++;
                  amount2++;
               }else{
                  number2 = Character.getNumericValue(equation[i+amount2])+number2*10;
                  amount2++;
               }
            }
            //makes it negative if it is suppoesed to be
            if(negative){
               number2 = -number2;
            }
            if(amount1 == 1||amount2 == 1){
            System.out.println("Here");//////////////
               System.out.println("This equation is invalid, it does not have the characters in a logical order.");
               char[] blank = new char[0];
               return blank;
            }
            double valued;
            if (equation[i] == '+'){
               valued = number1+number2;
            }else{
               valued = number1-number2;
            }
            char[] value = ("" + valued).toCharArray();
            char[] hold = new char[equation.length-amount1-amount2+1+value.length];
            int where = 0;
            int where2 = 0;   //recreates array with new values
            for(int j = 0; j < hold.length; j++){
               if(j <= i-amount1){
                  hold[j] = equation[j];
               }else if (i-amount1+value.length >= j){
                  hold[j] = value[where];
                  where++;
               }else{
                  hold[j] = equation[i+amount2+where2];
                  where2++;
               }
            }
            equation = hold;
            i = -1;
         } 
      }
      return equation;
   }
 }  
