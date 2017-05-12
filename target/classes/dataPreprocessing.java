import java.awt.Point;
import java.io.*;
import java.sql.BatchUpdateException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;
import javax.swing.text.html.HTMLDocument.Iterator;

import org.omg.CORBA.PRIVATE_MEMBER;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class dataPreprocessing {
	private double[][] linkMatrix;//原始数据，在算法中不断删除边
	private double[][] originMatrix;//用于存储原始数据
	public List<Double> Qlist;//Q的集合
	public List<edgeInfo> deleteEgdes;//删除的边
	public ArrayList<nodeInfo>  previousNodes;//Gn算法中一次已经成功入队的节点
	public ArrayList<nodeInfo> currentNodes;//Gn算法中的当前节点
	public ArrayList<ArrayList<nodeInfo>> resultList;
	public ArrayList<nodeInfo> record;//Gn算法中上一次访问过节点
	public double[] disArray;//Gn算法中一次的各个边的距离
	public double[] weightArray;//Gn算法中一次的各边的权重
	public double[][] disMatrix;//用于记录源节点与其他节点的距离
	public double[][] sumDisMatix;//记录边介数
	private int MAX_NUM;//最大的数目
	private int coperationNum;//统计社团的数目
	private int[] belong;//用于区别节点所述社团
	//节点对象
	public class nodeInfo {
		public List<Integer> parentNodes;//父节点
		public List<Integer> childrenNodes;	//子节点
		public int name;
		//public double weightOfNode;//点权,
		public nodeInfo(int n) {
			parentNodes=new ArrayList<Integer>();
			childrenNodes=new ArrayList<Integer>();
			name=n;
			//weightOfNode=1;
			
		}
		
	}
	//边对象
	private class edgeInfo
	{
		public int start;//开始的节点
		public int end;//结束的节点
		public double weightOfEdge;//边权
		public void setInfo(int a,int b,double c) {
		 this.start=a;
		 this.end=b;
		 this.weightOfEdge=c;
		}
	}
	//初始化变量
	private void init()
	{
		MAX_NUM=100;
		linkMatrix=new double[MAX_NUM][MAX_NUM];
		originMatrix=new double[MAX_NUM][MAX_NUM];
        Qlist=new ArrayList<Double>();
        deleteEgdes=new ArrayList<edgeInfo>();
        resultList=new ArrayList<ArrayList<nodeInfo>>();
        
	}
	private nodeInfo findNodeInfoById(int n,ArrayList<nodeInfo> l){
		for(int i=0;i<l.size();i++)
		{
			if(l.get(i).name==n)
				return l.get(i);
		}
		return null;
	}

    //计算权值并保存初始矩阵
	public void calWeights() {
		double max=0;
		double min =0;
		for(int i=0;i<MAX_NUM;i++)
		{
		    for(int j=0;j<MAX_NUM;j++)
		    {
		    	linkMatrix[i][j]=linkMatrix[i][j];//输入自己的赋值语句，计算linkMatrix矩阵
		    }
		}
		/*
		for(int i=0;i<MAX_NUM;i++)
			for(int j=0;j<MAX_NUM;j++)
				if(max<linkMatrix[i][j])
				{
					max=linkMatrix[i][j];
					//System.out.print(max+" "+"i"+i+" j"+j+"\n");
				}
		for(int i=0;i<MAX_NUM;i++)
			for(int j=0;j<MAX_NUM;j++)
				if(min>linkMatrix[i][j])
				{
					min=linkMatrix[i][j];
				}
		for(int i=0;i<MAX_NUM;i++)
		{
		    for(int j=0;j<MAX_NUM;j++)
		    {
		    	linkMatrix[i][j]=(linkMatrix[i][j]-min)/(max-min);
		    }
		 }*/
		for(int i=0;i<MAX_NUM;i++)
		{
		    for(int j=0;j<MAX_NUM;j++)
		    {
		         originMatrix[i][j]=linkMatrix[i][j];    	
		    }
	     }
	}
	public boolean isNodeOut(int n)
	{
		for(int i=0;i<MAX_NUM;i++)
		{
			if(linkMatrix[n][i]>0)
				return false;
		}
		return true;
	}
	public void calGn()
	{	
		while (true) {
			sumDisMatix=new double[MAX_NUM][MAX_NUM];
			coperationNum=0;
			belong=new int[MAX_NUM];
			//使用GN算法计算每一个点，从而得出边介数
			for(int i=0;i<MAX_NUM;i++)
				if(isNodeOut(i)==false)
				{
 				    calOneNodeGn(i);
				}
			
			double max=0;
			int x=0,y=0;
			//累加Q值
			for(int i=0;i<MAX_NUM;i++)
				for(int j=0;j<MAX_NUM;j++)
				{
					if(linkMatrix[i][j]>0)
						sumDisMatix[i][j]=sumDisMatix[i][j]/linkMatrix[i][j];
					else {
						sumDisMatix[i][j]=0;
					}
				}
			for(int i=0;i<MAX_NUM;i++)
				for(int j=0;j<MAX_NUM;j++)
					if(max<sumDisMatix[i][j])
					{
						max=sumDisMatix[i][j];
						x=i;
						y=j;
					}
			if(max==0)
				break;
			calQ();
			deleEdge(x,y);
		
			
		}
		//查找最大的Q值，此时为最佳划分
		double maxq=0;
		int index=0;
		for(int i=0;i<Qlist.size();i++)
		{
			if(maxq<Qlist.get(i))
			{
				maxq=Qlist.get(i);
				index=i;
			}
		}
		System.out.print("maxQ:"+maxq+" "+"index:"+index);
			
	}
	public double calQ()
	{
		double Q=0;
        double M=0;
        double sum=0;
        double []k=new double[MAX_NUM];
        for(int i=0;i<MAX_NUM;i++)
			for(int j=0;j<MAX_NUM;j++)
			{
				M+=linkMatrix[i][j];				
			}
        M=M/2;
        for(int i=0;i<MAX_NUM;i++)
        	for(int j=0;j<MAX_NUM;j++)
        	if(linkMatrix[i][j]>0)
        	{
        		k[i]+=linkMatrix[i][j];
        	}
        for(int i=0;i<MAX_NUM;i++)
			for(int j=0;j<MAX_NUM;j++)
			{
				if(belong[i]==belong[j])
				sum+=(linkMatrix[i][j]-k[i]*k[j]/(2*M));
			}
        Q=sum/(2*M);
        Qlist.add(Q);
       // System.out.print(Q+"\n");
 		return Q;
	}
	//计算一个节点
	public void calOneNodeGn(int n)
	{
		if(isNodeOut(n)==true)
			return ;
		disMatrix=new double[MAX_NUM][MAX_NUM];
		previousNodes=new ArrayList<dataPreprocessing.nodeInfo>();
		disArray=new double[MAX_NUM];
		weightArray=new double[MAX_NUM];
	     Boolean isStop=false; 
	     int deep1=2;
	    currentNodes=new ArrayList<dataPreprocessing.nodeInfo>();
	    singleNodeGNFirst(new nodeInfo(n), 1);
	    //不断往下寻找，建立以节点n为源节点的树
		while(isStop==false)
		{
		 isStop=true;			
		 record=currentNodes;
		 currentNodes=new ArrayList<dataPreprocessing.nodeInfo>();
		  for(int i=0;i<record.size();i++)
			 {
			   boolean ldl=singleNodeGN(record.get(i),deep1);//如果还有子节点，继续循环
			   if(ldl==true)
			         isStop=false;		   
			  }	   
		  deep1++;
	      // System.out.print(previousNodes.size()+"\n");
		  }
		for(int i=0;i<previousNodes.size();i++)
			{
			  nodeInfo ne=previousNodes.get(i);
			  if(ne.childrenNodes.size()==0)  
				  for(int j=0;j<ne.parentNodes.size();j++)
				  {
					  int x=ne.parentNodes.get(j);
					  disMatrix[i][x]=weightArray[x]/weightArray[i];
					  disMatrix[x][i]=disMatrix[i][x];
				  }
			}//对叶子节点进行赋初值
		double max=0;
		int num=0;
		for(int i=0;i<MAX_NUM;i++)
			if(max<disArray[i])
			{
				max=disArray[i];
				num=i;
			}
		//此时把previousNodes中与最后一个相同的节点归为同一社团，并且标记
		int first=previousNodes.size()-1;
		if(belong[first]==0)
		{
			coperationNum++;
			belong[first]=coperationNum;
		}

		for(int i=previousNodes.size()-1;i>=0;i--)
		{
			
			int x=previousNodes.get(i).name;
			if(belong[x]==0)
			   belong[x]=coperationNum;
		}
		//进行第二步,计算各边的权重
		for(int i=previousNodes.size()-1;i>=0;i--)
			singleSencondStep(previousNodes.get(i).name,n);
		//计算边介数
		for(int i=0;i<MAX_NUM;i++)
			for(int j=0;j<MAX_NUM;j++)
			{
				sumDisMatix[i][j]+=disMatrix[i][j];
			}
	}
	//计算各边的权重
	public void singleSencondStep(int n,int source)
	{
		nodeInfo neInfo=findNodeInfoById(n, previousNodes);
		for(int j=0;j<neInfo.parentNodes.size();j++)
		{
			nodeInfo pa=findNodeInfoById(neInfo.parentNodes.get(j), previousNodes);
			double q=1;
			for(int k=0;k<neInfo.childrenNodes.size();k++)
				q+=disMatrix[n][neInfo.childrenNodes.get(k)];
			int x=neInfo.parentNodes.get(j);
			if(pa.name!=source)
			{
			  if(weightArray[n]!=0)
			  {
			  disMatrix[n][x]=weightArray[x]*q/weightArray[n];
			  disMatrix[x][n]=disMatrix[n][x];
			  }
			}
			else {
				 disMatrix[n][x]=q;
				 disMatrix[x][n]=disMatrix[n][x];	
			}
			
		}
	
	}
	//广度优先遍历源节点，并进行标记
	public Boolean singleNodeGNFirst(nodeInfo nn,int deep)
	{		
		//System.out.println(deep+"\n");
		boolean res=true;
		int n=nn.name;
		previousNodes.add(nn);			
		for(int i=0;i<MAX_NUM;i++)
		{
			if(linkMatrix[n][i]>0)
			{   
			
				nodeInfo ne=new nodeInfo(i);			
				nodeInfo parentInfo=findNodeInfoById(n, previousNodes);				
				parentInfo.childrenNodes.add(i);//为父子关系
				ne.parentNodes.add(n);			
				currentNodes.add(ne);
				disArray[i]=deep;
			    weightArray[i]=1;
			}
		}
			
		 for(int j=0;j<MAX_NUM;j++)
	     {
		    if(linkMatrix[n][j]>0)
			 {
				if(disArray[j]==0)
				   res=false;
			 }
		 }
		return res;
	}
	//广度优先遍历节点
	public Boolean singleNodeGN(nodeInfo nn,int deep)
	{		
		//System.out.println(deep+"\n");
		boolean res=false;
		int n=nn.name;
		
		if(findNodeInfoById(n, previousNodes)==null)
			previousNodes.add(nn);		
		for(int i=0;i<MAX_NUM;i++)
		{
			if(linkMatrix[n][i]>0)
			{   
			    
				nodeInfo ne=new nodeInfo(i);			
				
				if(findNodeInfoById(n, record)!=null&&(findNodeInfoById(i, record)!=null))
					break;
				if((findNodeInfoById(i, previousNodes)==null))
				{
				  nodeInfo parentInfo=findNodeInfoById(n, previousNodes);
				
				  if(findNodeInfoById(i, currentNodes)==null)
				  {
					  parentInfo.childrenNodes.add(i);//为父子关系
					  ne.parentNodes.add(n);//
					  currentNodes.add(ne);//如果在当前访问节点中为空并且从未被访问过
					  res=true;
				  }
				  else {
					  {
						  parentInfo.childrenNodes.add(i);//为父子关系
						  findNodeInfoById(i, currentNodes).parentNodes.add(n);
						  res=true;
					  }
				}
				  if(disArray[i]==0)
					{
					   disArray[i]=deep;
			
					   weightArray[i]=weightArray[parentInfo.name];
					}
				else if(disArray[i]==deep)
				{
					
					 weightArray[i]=weightArray[n]+weightArray[i];
				}
				else if(disArray[i]<deep)
					;
				else {
					;
				 }
				}
			
			}
		}
		/* for(int j=0;j<MAX_NUM;j++)
	     {
		    if(linkMatrix[n][j]>0)
			 {
				if(disArray[j]==0)
				   res=false;
			 }
		 }*/
		return res;
	}
	public void deleEdge(int start,int end)
	{
		edgeInfo deInfo=new edgeInfo();
		deInfo.setInfo(start, end, linkMatrix[start][end]);
		deleteEgdes.add(deInfo);
		linkMatrix[start][end]=0;
		linkMatrix[end][start]=0;
	}
	public void printResult()
	{
		double max=0;
		int index=0;
		for(int i=0;i<Qlist.size();i++)
		{
			if(max<Qlist.get(i))
			{
				max=Qlist.get(i);
				index=i;
			}
		}
		for(int i=0;i<index;i++)
		{
			 edgeInfo eInfo=deleteEgdes.get(i);
			 int ks=eInfo.start;
			 int ke=eInfo.end;
			 originMatrix[ks][ke]=0;
			 originMatrix[ke][ks]=0;
		}
	    
		File file = new File("d:/temp", "res.txt"); 
		try {  
            file.createNewFile(); // 创建文件  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
		try {
			FileOutputStream in = new FileOutputStream(file);  
			for(int i=0;i<MAX_NUM;i++)
				for(int j=0;j<MAX_NUM;j++)
				{
					String str =""+i+" "+j+" "+originMatrix[i][j]+"\n";  
			        byte bt[] = new byte[1024];  
			        bt = str.getBytes();  
			      
			            try {  
			                in.write(bt, 0, bt.length);  
			                //in.close();  
			                // boolean success=true;  
			                // System.out.println("写入文件成功");  
			            } catch (IOException e) {  
			                // TODO Auto-generated catch block  
			                e.printStackTrace();  
			            }  
			        
					
				}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
    public static void main(String[] args) 
    {
   
		dataPreprocessing testDataPreprocessing=new dataPreprocessing();
		testDataPreprocessing.init();	
		/*for(int i=0;i<testDataPreprocessing.MAX_NUM;i++)
			{
			for(int j=0;j<testDataPreprocessing.MAX_NUM;j++)
			
			{
				System.out.print(testDataPreprocessing.linkMatrix[i][j]);
				System.out.print(" ");
			}
			System.out.println("\n");
			}*/
		testDataPreprocessing.calWeights();
	     testDataPreprocessing.linkMatrix=new double[testDataPreprocessing.MAX_NUM][testDataPreprocessing.MAX_NUM];
		testDataPreprocessing.linkMatrix[0][1]=1;
		testDataPreprocessing.linkMatrix[0][2]=1;
		testDataPreprocessing.linkMatrix[1][0]=1;
	    testDataPreprocessing.linkMatrix[1][3]=1;
		testDataPreprocessing.linkMatrix[2][0]=1;
		testDataPreprocessing.linkMatrix[2][3]=1;
		testDataPreprocessing.linkMatrix[2][4]=1;
		testDataPreprocessing.linkMatrix[3][1]=1;
		testDataPreprocessing.linkMatrix[3][2]=1;
		testDataPreprocessing.linkMatrix[3][5]=1;
		testDataPreprocessing.linkMatrix[4][2]=1;
		testDataPreprocessing.linkMatrix[4][5]=1;
		testDataPreprocessing.linkMatrix[4][6]=1;
		testDataPreprocessing.linkMatrix[5][3]=1;
		testDataPreprocessing.linkMatrix[5][4]=1;
		testDataPreprocessing.linkMatrix[6][4]=1;
		testDataPreprocessing.calGn();
		testDataPreprocessing.printResult();
	    System.out.print("ok");		
	
    }
			
}
