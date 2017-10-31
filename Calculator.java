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
   
   public static void equation (Scanner input){
      System.out.println("Would you like to enter an equation (enter yes if you would like to any other anser will be taken as no)?");
      String more = input.nextLine();
      while(more.equals("yes")){
         System.out.println("Please enter your equation (do not input any spaces).");
         char[] equation = input.next().toCharArray();
         boolean correct = check(equation);
         if(correct){
            equation = parenthesis(equation, -1);
            equation  = calculate(equation);
            System.out.println(equation);
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
          equation[i] == '8'|| equation[i] == '9'|| equation[i] == '0'|| equation[i] == '+'|| equation[i] == '-'|| equation[i] == '='|| equation[i] == ' ' ||
           equation[i] == '*'|| equation[i] == '/'|| equation[i] == '^'){
         }else{
            return false;
         }
      }
      return true;
   }
   
   //calculates sub formulas contained in parenthesis
   public static char[] parenthesis(char[] equation, int place){
      for (int i = place +1 ; i < equation.length; i++){
         if(equation[i] == '('){
            equation = parenthesis(equation, i);
         }else if(equation[i] == ')' && place != -1 && i != place+1){
            char[] value = calculate(Arrays.copyOfRange(equation, place+1, i-1));
            char[] hold = new char[equation.length-i+place+value.length];
            for(int j = 0; j < hold.length; j++){
               if(j < place){
                  hold[j] = equation[j];
               }else if (place-1+value.length >= j){
                  hold[j] = value[j-place];
               }else{
                  hold[j] = equation[i+1+(j-i)];
               }
            }
            equation = hold;
         }else if(equation[i] == ')' && place != -1 && i == place+1){  
             equation[place] = 0;
             equation[i] = 0;
         }else if(equation[i] == ')' && place == -1){
            System.out.println("This equation is invalid, it contains incorrect characters");
         }
      }
      return equation;
   }
   
   //calculates the equation based on the order of operations
   public static char[] calculate(char[] equation){
      return equation;
   }
 }  
