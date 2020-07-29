

/* Read input from STDIN. Print your output to STDOUT*/

import java.io.*;
import java.util.*;
public class CoronaWarriors {
   public static void main(String args[] ) throws Exception {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    
    String line[] =  br.readLine().split(" ");
     n = Integer.parseInt(line[0]);
     m = Integer.parseInt(line[1]);
    
    arr = new int[m][n];
  boolean bool[] = new boolean[n];
 for(int i = 0 ;i<m;i++)
 {  
	 line = br.readLine().split(" ");
	 for(int j = 0;j<n;j++)
	 {
		arr[i][j] = Integer.parseInt(line[j].trim());
	 }
 }
 
 //ArrayList<Integer> list = new ArrayList<>();
 
 

 

 dp = new int[21][21][1025];

int ans  = Integer.MAX_VALUE;
 for(int i = 0 ;i<m;i++)
 {  
   ans = Math.min(calculate(i,1,arr[i][0],0),ans);
	 
 }
 
 
 
 bw.write(ans+"\n");
 bw.flush();
 
}
   
   static boolean[] copyOf(boolean b[])
   {
	   boolean boo[] = new boolean[b.length];
	   for(int i = 0 ;i<b.length;i++)
	   {
		   boo[i]= b[i];
	   }
	   return boo;
   }
   
   static  int[][] arr;
   static int n;
   static int m;
   static int dp[][][];
   
   
   static int calculate(int i,int j, int k,int l)
   {   
	   if(dp[i][j][l]!=0)
	   {
		  // System.out.println("hi");
		   return dp[i][j][l]+k;
	   }
		 
		 if(j==n)
		 {
			//dp[i][j][l] = k;
			return k;
		 }
		 else
		 {  
		    int temp =  calculate(i,j+1,arr[i][j],l); 
		     int new_l =  l | (1 << i);
			 for(int e = 0;e<m;e++)
			 {
				 if(((1<<e)&new_l)==0)
				 {  
					temp = Math.min(calculate(e,j+1,arr[e][j],new_l),temp);
				 }
			 }
			 dp[i][j][l] = temp;
			 return temp+k;
		 }
		 
   }
   
   static int booleanToInt(boolean b[])
   {
	   int j = 1;
			   int ans = 0;
	   for(int i = n-1;i>=0;i--)
	   {
		   if(b[i])
		   {
			   ans = ans +j;
		   }
		   j = 2*j;
	   }
	   return ans;
   }
   
}
