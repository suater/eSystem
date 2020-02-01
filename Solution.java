/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esystem;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*; 

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface FamilyBudget {
	String userRole() default "GUEST";
    // default we can use zero
      int value() default 0;
}

class FamilyMember {
 /* SENIOR budget-moneySpend=?
 * A)Spend: 40 Budget Left: 60 sum result 100
 * C)Spend: 75 Budget Left: 25 sum result 100
 * so we can use 100 for value
 */
  @FamilyBudget(userRole = "SENIOR", value = 100)
  public void seniorMember(int budget, int moneySpend) {
    System.out.println("Senior Member");
    System.out.println("Spend: " + moneySpend);
    System.out.println("Budget Left: " + (budget - moneySpend));
  }
/* JUNIOR budget-moneySpend=?
 * B)Spend: 45 Budget Left: 5 sum result 50
 * so we can use 50 for value
 */
  @FamilyBudget(userRole = "JUNIOR", value = 50)
  public void juniorUser(int budget, int moneySpend) {
    System.out.println("Junior Member");
    System.out.println("Spend: " + moneySpend);
    System.out.println("Budget Left: " + (budget - moneySpend));
  }
}
public class Solution {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int testCases = Integer.parseInt(in.nextLine());
		while (testCases > 0) {
			String role = in.next();
			int spend = in.nextInt();
			try {
				Class annotatedClass = FamilyMember.class;
				Method[] methods = annotatedClass.getMethods();
				for (Method method : methods) {
					if (method.isAnnotationPresent(FamilyBudget.class)) {
						FamilyBudget family = method
								.getAnnotation(FamilyBudget.class);
						String userRole = family.userRole();
                        //Family budget limit value 
						int budgetLimit = family.value();
						if (userRole.equals(role)) {
                            // Spending cannot be larger than our budget
							if(budgetLimit>=spend){
								method.invoke(FamilyMember.class.newInstance(),
										budgetLimit, spend);
							}else{
								System.out.println("Budget Limit Over");
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			testCases--;
		}
	}
}




