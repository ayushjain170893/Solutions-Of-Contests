 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;
 
 
 
public class XYZQueries {
	private static class FastReader 
	{ 
	    BufferedReader br; 
	    StringTokenizer st; 
 
	    public FastReader() 
	    { 
	        br = new BufferedReader(new
	                 InputStreamReader(System.in)); 
	    } 
 
	    String next() 
	    { 
	        while (st == null || !st.hasMoreElements()) 
	        { 
	            try
	            { 
	                st = new StringTokenizer(br.readLine()); 
	            } 
	            catch (IOException  e) 
	            { 
	                e.printStackTrace(); 
	            } 
	        } 
	        return st.nextToken(); 
	    } 
 
	    int nextInt() 
	    { 
	        return Integer.parseInt(next()); 
	    } 
 
	    long nextLong() 
	    { 
	        return Long.parseLong(next()); 
	    } 
 
	    double nextDouble() 
	    { 
	        return Double.parseDouble(next()); 
	    } 
 
	    String nextLine() 
	    { 
	        String str = ""; 
	        try
	        { 
	            str = br.readLine(); 
	        } 
	        catch (IOException e) 
	        { 
	            e.printStackTrace(); 
	        } 
	        return str; 
	    } 
	} 
	
	static int [][]lookup ; 
	
  
	static void buildSparseTable(int arr[], int n) 
    {    
         lookup = new int[n][30];
        // Initialize M for the intervals with length 1 
        for (int i = 0; i < n; i++) 
            lookup[i][0] = i; 
      
        // Compute values from smaller to bigger intervals 
        for (int j = 1; (1 << j) <= n; j++) { 
      
            // Compute minimum value for all intervals with 
            // size 2^j 
            for (int i = 0; (i + (1 << j) - 1) < n; i++) { 
      
                // For arr[2][10], we compare arr[lookup[0][7]]  
                // and arr[lookup[3][10]] 
                if (arr[lookup[i][j - 1]] <  
                            arr[lookup[i + (1 << (j - 1))][j - 1]]) 
                    lookup[i][j] = lookup[i][j - 1]; 
                else
                    lookup[i][j] =  
                            lookup[i + (1 << (j - 1))][j - 1]; 
            } 
        } 
    } 
      
    // Returns minimum of arr[L..R] 
    static int query(int L, int R,int[] arr) 
    { 
          
        // Find highest power of 2 that is smaller 
        // than or equal to count of elements in given 
        // range. For [2, 10], j = 3 
        int j = (int) (Math.log(R - L + 1)/Math.log(2)); 
      
        // Compute minimum of last 2^j elements with first 
        // 2^j elements in range. 
        // For [2, 10], we compare arr[lookup[0][3]] and 
        // arr[lookup[3][3]], 
        if (arr[lookup[L][j]] <= arr[lookup[R - (1 << j) + 1][j]]) 
            return lookup[L][j]; 
      
        else
            return lookup[R - (1 << j) + 1][j]; 
    } 
	
public static void main(String[] args) throws IOException {
	FastReader br = new FastReader();
	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    
	int n = br.nextInt();
	int arr[][] = new int[n+1][32];
	
	for(int i = 1;i<=n;i++)
	{
	  int a = br.nextInt();
	     for(int j = 0;j<32;j++)
	     {
	    	 if((a&(1<<j))>0)
	    	 {
	    		arr[i][j]=1; 
	    	 }
	    	 else
	    	 {
	    		 arr[i][j]= 0;
	    	 }
	     }
	}
	 Graph G = new Graph(n);
     for(int i = 0;i<n-1;i++)
     {
  	  
  	   int u = br.nextInt();//Integer.parseInt(line[0]);
  	   int v = br.nextInt();//Integer.parseInt(line[1]);
  	   G.addEdge(u, v);
     }
    
     
     int root = G.findRootForMinHieght();
     Tree  tree  = new Tree(n,root);
     Queue<Integer> que = new LinkedList<Integer>();
     Queue<Integer> hque = new LinkedList<Integer>();
     Queue<int[]> bitsQue  = new LinkedList<>();
     
     que.add(root);
     hque.add(0);
     bitsQue.add(arr[root]);
     boolean visited[] = new boolean[n+1];
     
     while(!que.isEmpty())
     {
  	   int p = que.poll();
  	   int h = hque.poll();
  	   int[] array = bitsQue.poll();
  	   visited[p] = true;
  	   for(int c : G.adj[p])
  	   {
  		   if(!visited[c]) {
  			 tree.addchild(p, c,h+1);
  		     que.add(c);
  		     hque.add(h+1);
  		     for(int i = 0;i<32;i++)
  		     {
  		    	arr[c][i] = arr[c][i]+array[i];
  		    	
  		     }
  		   bitsQue.add(arr[c]);
  		   visited[c] = true;
  		   }
  	         
  	   }
     }
    
    tree.indx= 0;
    
    tree.eulerTree(root);
    
 
	buildSparseTable(tree.hieghtRout, tree.indx);
    
	int q = br.nextInt();
	
	while(q-->0)
	{
		int x = br.nextInt();
		int y = br.nextInt();
		int z = br.nextInt();
		
	 if(x==1)
	 {
		  bw.write(calForOne(tree,y,z,arr)+"\n");
	 }
	 else if(x==2)
	 {
		 bw.write(calForTwo(tree,y,z,arr)+"\n");
	 }
	 else if(x==3)
	 {
		 bw.write(calForThree(tree,y,z,arr)+"\n");
	 }
	 else if(x==4)
	 {
		 bw.write(calForFour(tree,y,z,arr)+"\n");
	 }
    
	}
    bw.flush(); 
     
}
static long mod = 1000000007;
static long calForOne(Tree tree,int y,int z,int[][] arr)
{     int l = tree.firstO[y];
      int r = tree.firstO[z];
      if(l>r)
  	{
  	 int temp = r;
  	 r = l ;
  	 l = temp;
  	}
  	
	   int lca = query(l, r,tree.hieghtRout);
	   
		int g = tree.eulerRout[lca];
		
	    int pg = tree.list[g].parent;
	    
	    int h1 = tree.list[y].hieght;
	    int h2 = tree.list[z].hieght;
	    
	    int lcaH = tree.list[g].hieght;
	    
	   long ans = 0;
	   long pow = 1;
	    for(int i = 0;i<32;i++)
	    {
	     long total = arr[y][i]+arr[z][i] - arr[pg][i] - arr[g][i];
	     
	    
	     ans  = ans + (((pow%mod)*(total%mod))%mod); 
	     pow = (pow%mod*2)%mod;
	    }
	
return ans%mod;
}
static long calForTwo(Tree tree,int y,int z,int[][] arr)
{     int l = tree.firstO[y];
      int r = tree.firstO[z];
      if(l>r)
  	{
  	 int temp = r;
  	 r = l ;
  	 l = temp;
  	}
  	
      int lca = query(l, r,tree.hieghtRout);
	   
		int g = tree.eulerRout[lca];
		
	    int pg = tree.list[g].parent;
	    
	    int h1 = tree.list[y].hieght;
	    int h2 = tree.list[z].hieght;
	    
	    int lcaH = tree.list[g].hieght;
	    
	   long ans = 0;
	   long pow = 1;
	    for(int i = 0;i<32;i++)
	    {
	     long K1 = arr[y][i]+arr[z][i] - arr[pg][i] - arr[g][i];
	      long K0 =  (h1 + h2 - 2*lcaH +1)-K1;
	      
	      K1 =  ((K1*(K1-1))%mod*(power(2)%mod))%mod + (K1*K0)%mod;
	      ans  = (ans%mod + (K1%mod*pow%mod)%mod)%mod;
	      pow = (pow%mod*2)%mod;
	    }
	
return ans%mod;
}  
 
static long calForThree(Tree tree,int y,int z,int[][] arr)
{     int l = tree.firstO[y];
      int r = tree.firstO[z];
      if(l>r)
  	{
  	 int temp = r;
  	 r = l ;
  	 l = temp;
  	}
  	
      int lca = query(l, r,tree.hieghtRout);
		int g = tree.eulerRout[lca];
		
	    int pg = tree.list[g].parent;
	    
	    int h1 = tree.list[y].hieght;
	    int h2 = tree.list[z].hieght;
	    
	    int lcaH = tree.list[g].hieght;
	    
	   long ans = 0;
	   long pow = 1;
	    for(int i = 0;i<32;i++)
	    {
	     long K1 = arr[y][i]+arr[z][i] - arr[pg][i] - arr[g][i];
	     long K0 =  (h1 + h2 - 2*lcaH +1)-K1;
	      
	      K1 =  ((K1*(K1-1)*(K1-2))%mod*(power(6)%mod))%mod + ((K1*K0*(K0-1))%mod*power(2)%mod)%mod
	    		  + (((K1*(K1-1)*K0)%mod*power(2)%mod)%mod);
	
	      ans  = (ans%mod + (K1%mod*pow%mod)%mod)%mod;
	      pow = (pow%mod*2)%mod;
	    }
	
return ans%mod;
}  
 
static long calForFour(Tree tree,int y,int z,int[][] arr)
{     int l = tree.firstO[y];
      int r = tree.firstO[z];
      if(l>r)
  	{
  	 int temp = r;
  	 r = l ;
  	 l = temp;
  	}
  	
      int lca = query(l, r,tree.hieghtRout);
	   
		int g = tree.eulerRout[lca];
		
	    int pg = tree.list[g].parent;
	    
	    int h1 = tree.list[y].hieght;
	    int h2 = tree.list[z].hieght;
	    
	    int lcaH = tree.list[g].hieght;
	    
	   long ans = 0;
	   long pow = 1;
	    for(int i = 0;i<32;i++)
	    {
	     long K1 = arr[y][i]-arr[g][i] +arr[z][i] - arr[g][i] + (arr[g][i] - arr[pg][i]);
	     long K0 =  (h1 + h2 - 2*lcaH +1)-K1;
	      long K3 = 0;
	      K3 =   (K1*(K1-1)*(K1-2))%mod;
	      K3 = 	  (((K3%mod*(K1-3)%mod)%mod)%mod*power(24)%mod)%mod ;
	       	K3 = K3 + ((K1*K0)%mod*(((K0-1)*(K0-2))%mod + ((K1-1)*(K1-2))%mod))%mod*power(6)%mod;
           K3 = K3%mod + ((((K1-1)*(K0-1))%mod)*((K1*K0)%mod))%mod*power(4)%mod;
           
           
	      ans  = (ans%mod + (K3%mod*pow%mod)%mod)%mod;
	      pow = (pow%mod*2)%mod;
	    }
	
return ans%mod;
}  
// To compute x^y under modulo m 
static long power(long x)  
{ 
	
	if(x==1)
    {
		return 1;
    }
	if(x==2)
	{
		return 500000004;
	}
	if(x ==6)
	{
		return 166666668;
	}
	
	if(x==24)
	{
		return 41666667;
	}
	
	if(x==4)
	{
	return	250000002;
	}
	if(x==3)
	{
		return 333333336;
	}
	
	
return 0;
} 
}
 
class Graph{
	
	  public	int v; 
	  public  List<Integer> adj[];
	  public  int degree[]; 
	    public  Graph(int v){
	    	this.v = v; 
	        adj = new ArrayList[v+1]; 
	        degree = new int[v+1]; 
	        for(int i = 1;i<=v;i++)
	        {
	        	adj[i] = new ArrayList<Integer>();
	        }
	        
	    }          
	   public  void addEdge(int v, int w)
	   {
		   adj[v].add(w);
		   adj[w].add(v);
		   degree[v]++;
		   degree[w]++;
	   }
	   
	   public int  findRootForMinHieght()
	   {
		   Queue<Integer> que = new LinkedList<Integer>();
		    int x = v;
		   for (int i = 1; i <= this.v; i++) 
		   {     if (degree[i] == 1) 
		            que.add(i); 
		  
		   }
		   
		   while (x > 2) 
		   {  
			   int t = que.poll(); 
			   x--;
			   for (int k : adj[t])
			   {
				   degree[k]--;
				 if(degree[k]==1)
					 que.add(k);
			   }
			   
		   }
			  
		  while(que.size()>1)
		  {
			  que.poll();
		  }
		  
		  return que.poll();
		   }
	   }
 
class node{
	long oil;
	int parent;
	int hieght;
	List<Integer> child;
	public node(int parent)
	{
	 this.parent = parent;
	 child = new ArrayList<>();
	}
	}
 
	 class Tree{
	 int root;
	   node list[];
	   int eulerRout[];
	   int hieght[];
	   int indx;
	   int hieghtRout[];
	   int firstO[];
	   public Tree(int n ,int root)
	   {
		   this.list = new node[n+1];
		   this.hieghtRout = new int[2*n+2];
		   this.eulerRout = new int[2*n+2];
		   this.hieght = new int[n+1];
		    this.firstO = new int[n+1];
		   this.root = root;
		   node r = new node(0);
		    list[root] = r;
		    list[root].hieght = 0;
		    Arrays.fill(firstO,-1);
	   }
	   
	   public void addchild(int parent, int child,int hieght)
	   { 
		   if(list[child]==null)
		   {
			  list[child] = new node(parent);
		   }
		 this.list[parent].child.add(child);
		 list[child].parent = parent;
		 list[child].hieght = hieght;
	   }
	   
	   public void eulerTree(int u) 
	   {    
	      
	      
	       boolean visited[]  = new boolean[this.list.length];
	       Stack<Integer> stack = new Stack<Integer>();
	       stack.push(u);
	       while(!stack.isEmpty()) {
	       
	    	  int x = stack.pop();
	    	  
	    	  
	    	   if(firstO[x]==-1)
	       {
	    	   firstO[x] = indx;
	        }  
	    	   
	    	   if(this.list[x].child.isEmpty()&&visited[x])
	    	   {
	    		   continue;
	    	   }
	    	   eulerRout[indx] = x;
	   	         hieghtRout[indx] =  this.list[x].hieght;
	   	          indx++; 
	   	          
	   	       if(!visited[x])
		    	  {
	   	    	   
	       for(int it : this.list[x].child) 
	       {      if(!visited[it])
	                 {stack.push(x);
	    	   stack.push(it); 
	       }}
	       
		    	  }
	   	       
	   	       visited[x] = true;
	   	       
	       } 
	   } 
	   
	}
