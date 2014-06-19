package com.jain.sudukosolver;

public class Solver9 {
	private int arr[][];
	private int barr[][];
	private int carr[];
	private int rarr[];
	private int parr[][];
	private int poss[][];
	private int copy[][];
	public Solver9(int arr[][]){
		this.arr = arr;
		barr = new int[3][3];
		rarr = new int[9];	
		carr = new int[9];
		parr = new int[9][9];
		poss = new int[9][9];
		copy = new int[9][9];
		for(int i =0; i<9; i++){
			for(int j =0; j<9; j++){
				int temp = arr[i][j];
				copy[i][j] = temp;
				poss[i][j] = 511;
				arr[i][j] = 0;
				if(temp==0){
					parr[i][j] = -1;
					arr[i][j] = -1;
				}else{
					parr[i][j] = 1;
					temp--;
					arr[i][j]+= Math.pow(2, temp);
				}
			}
			//System.out.println();
		}
	}
	public int[][] getArr(){
		int toRet[][] = new int[9][9];
		for(int i = 0; i<9; i++){
			for(int j = 0; j<9; j++){
				toRet[i][j] = Integer.toBinaryString(arr[i][j]).length();
				}
		}
		return toRet;
	}
	public boolean check(){
		for(int i = 0; i<9; i++){
			rarr[i] = 511;
			carr[i] = 511;
		}
		for(int x = 0; x<3; x++){
			for(int y = 0;y<3;  y++){
				barr[x][y] = 511;
			}
		}
		for(int i=0; i<9;i++){
			for(int j=0; j<9;j++){
				if(parr[i][j]!=-1){
					int s = arr[i][j];
					if((rarr[i]&s) ==0){
						return false;
					}
					if((carr[j]&s) ==0){
						return false;
					}
					if((barr[i/3][j/3]&s) ==0){
						return false;
					}
					/*if(Integer.toBinaryString(rarr[i]).charAt(8-(copy[i][j]-1)) == '0'){
						return false;
					}
					if(Integer.toBinaryString(carr[j]).charAt(8-(copy[i][j]-1)) == '0'){
						return false;
					}
					if(Integer.toBinaryString(barr[i/3][j/3]).charAt(8-(copy[i][j]-1)) == '0'){
						return false;
					}*/
					rarr[i]-= arr[i][j];
					carr[j]-= arr[i][j];
					barr[i/3][j/3]-= arr[i][j];
				}
			}
		}
		return true;
	}
	public boolean solve(){
		int counter = 0;
		int x=0,y=0, replace=-1, num=0, dir=1, ind=0;
		
		try{
			while(x<9){
				if(parr[x][y]==-1){
					replace = Integer.lowestOneBit(barr[x/3][y/3]&carr[y]&rarr[x]&poss[x][y]);
					num = arr[x][y];
					if(num>-1){
						barr[x/3][y/3]+=num;
						carr[y]+=num;
						rarr[x]+=num;
					}
					if(replace==0){
						arr[x][y]= -1;
						poss[x][y] = 511;
						dir = -1;
					}
					else{
						arr[x][y] = replace;
						barr[x/3][y/3]-=replace;
						carr[y]-=replace;
						rarr[x]-=replace;
						poss[x][y]-=replace;
						dir = 1;
					}
				}
				if(dir==1){
					y++;
					if(y==9){
						y = 0;
						x++;
					}
				}else{
					y--;
					if(y==-1){
						y = 8;
						x--;
					}
				}
			}

		}catch(Exception e){
			return false;
		}
		return true;
	}
}
