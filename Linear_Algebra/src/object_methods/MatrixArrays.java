package object_methods;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.*;

public class MatrixArrays {
	private double[][] matrix, subMat;
	private int r; 
	private int c; 
	
	public MatrixArrays(double[][] m) {
		boolean goodM = true; 
		double[] test = new double[m[0].length];
		for(double[] n: m) {
			if(n.length == test.length) goodM = true; 
			else goodM = false; 
			test = n;
		}
		if(!goodM) throw new IllegalArgumentException("Bad Matrix");
		matrix = m; 
		this.r = m.length; 
		this.c = m[0].length; 
		subMat = Arrays.copyOfRange(m, 0, 1);
	}
	
	
	
/*								PRIVATE METHODS																		*/
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private boolean check1(MatrixArrays a) {
		if(matrix.length == a.matrix.length && matrix[0].length == a.matrix[0].length) return true; 
		return false;
	}
	
	private boolean check2(MatrixArrays a) {
		if((c == a.r)) return true; 
		return false; 
	}
	
	private boolean check3(MatrixArrays a) {
		if(c == a.c && r == a.r) return true; 
		return false; 
	}
	
	private boolean check4() {
		//this function checks whether the matrix is already in REF 
		/*
		double[][] check = new double[r][c];
		int col = 0; 
		//creates a matrix with the exact same dimensions as the this.matrix, in RREF
		for(int i = 0; i < check.length; i++) {
			if(col < check[i].length) {
				check[i][col] = 1; 
				++col; 
			}
		}*/
		//checks whether this.matrix is in REF 
		
		/*
		*Only works for square matrices, not for rectangles.*
		a_loop:
		for(int y = 0; y < check.length; y++) {
			for(int z = 0; z < check[0].length; z++) {
				if(z <= c) {
					if(check[y][z] == matrix[y][z]) val = true; 
					else {
						val = false; 
						break;
					}
					c++;
				}
				else break;
			
			}
		}*/
		/*this part checks compares row by row, check and matrix.
		it checks to (0,0)r1,(1,1)r2,(2,2)r3,... ---Note (i,j)---
		ONLY WORKS FOR DIAGNALly reduced matrices
		top:
		for(int i = 0; i < check.length; i++) {
			for(int j = 0; j < check[0].length; j++) {
				if(j <= i) {
					if(check[i][j] == matrix[i][j]) {
						val = true; 
						if(i == j) break;  
					}
					else {
						val = false; 
						break top; 
					}
				}
			}
		}*/
		
		//goes to the first row, finds the first 1, whether it is the first number or following some zeros
		//if the first non zero number in a row is not 1, return false and break out of top loop
		//if it finds a one in correct conditions (after 0s or as first num) stores that index to check for 1s in later rows 
		//if a 1 is present in later rows in a col before the previous 1, return false and break top 
		boolean val = false; 
		int nextOne = 0;
		top:
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				if(matrix[i][j] == 0) continue; 
				if(matrix[i][j] != 0 && matrix[i][j] != 1) {
					val = false; 
					break top;
				}
				if(matrix[i][j] == 1 && j <= nextOne) {
					if((j == nextOne && nextOne != 0) || (j < nextOne)) {
						val = false; 
						break top;
					}
				}
				if(matrix[i][j] == 1 && j >= nextOne) {
					nextOne = j; 
					val = true; 
					continue top;
				}
			}
		}
		return val; 
	}
	
	private boolean check5() {
		//checks whether matrix is a zero matrix 
		double[][] check = new double[r][c];
		if(Arrays.deepEquals(matrix, check)) return true; 
		return false; 
	}
	
	private boolean check6() {
		//this function will check if a matrix is in RREF 
		
		
		/*
		// THIS ONLY CONSIDERS MATRICES THAT WILL ACTUALLY HAVE A PERFECT REF
		 * DOES NOT CONSIDER 1 0 2 0
		 * 					 0 2 1 0
		 * 					 0 0 0 1
		 			THIS IS RREF AS WELL !!!
		top:
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				if(j == matrix.length - 1 && matrix[i][j] == 0) {
					continue top;
				}
				
				if(matrix[i][j] == 0) continue; 
				
				if(matrix[i][j] != 0 && matrix[i][j] != 1) {
					retVal = false; 
					break top;
				}
				
				if(matrix[i][j] == 1 && j <= nextOne) {
					if((j == nextOne && nextOne != 0) || (j < nextOne)) {
						retVal = false; 
						break top;
					}
				}
				
				if(matrix[i][j] == 1 && j >= nextOne && i == curRow) {
					nextOne = j; 
					retVal = true; 
					curRow++;
				}
				
			}
			curRow++;
		}*/
		
		//starts from the last row of the matrix because we assume the matrix we are checking 
		//is in REF. If a one is present, it checks whether all numbers above it are 0. 
		//does this for each row with a leading one. We can do this because without looking 
		//at numbers below the leading ones because the matrix is already in REF, hence 
		//below leading ones are zeros.
		int nextOneRow = matrix.length - 1, curInd = 0; 
		double[] oneChecker; 
		boolean retVal = true; 
		top:
		while(true) {
			oneChecker = matrix[nextOneRow];
			if(isAllZero(oneChecker)) {
				if(nextOneRow > 0) nextOneRow--;
				continue; 
			}
			for(int i = 0; i < matrix[0].length; i++) if(oneChecker[i] == 1) {
				curInd = i; 
				break; 
			}
			
			fst:
			for(int j = curInd; j == curInd; j++) {
				for(int i = nextOneRow - 1; i >= 0; i--) {
					if(matrix[i][j] == 0) continue; 
					else {
						retVal = false; 
						break top; 
					}
				}
			}
			
			if(nextOneRow > 0) nextOneRow--;
			else return retVal;
		}
		return retVal;
	}
	
	private int REFcol() {
		//this function will return if there are at least 2 non-zero points in a column
		int count = 0;
		int col = 0; 
		the_loop:
		for(int i = 0; i < matrix[0].length; i++) {
			count = 0;
			for(int j = 0; j < matrix.length; j++) {
				if(matrix[j][i] != 0) count++;
				if(count == 2) {
					col = i;
					break the_loop; 
				}
			}
		}
		return col; 
	}
		
	private boolean check7() {
		//checks if matrix is a square matrix
		if(c == r) return true; 
		return false; 
	}
	
	private boolean check8() {
		//checks whether each leading one has all zeros under it 
		
		/*
		if(r == 1 && c == 1) {
			return true; 
		}
		boolean val = true; 
		int curCheck = 0; 
		top:
		for(int i = 0; i < matrix[0].length; i++) {
			for(int j = 0; j < matrix.length; j++) {
				
				if(j < curCheck) continue; 
				if(matrix[j][i] != 0) {
					val = false; 
					break top; 
				}
				curCheck++;
			}
		}
		return val; */
		int curCol = 1; 
		boolean val = true; 
		t:
		for(int i = 0; i < matrix[0].length;i++) {
			for(int j = curCol; j < matrix.length;j++) {
				if(matrix[j][i] != 0) {
					val = false; 
					break t;
				}
				else continue;
			}
			curCol++;
		}
		return val;
		
		
		
		
	}
	
	
	
	//finds the min number in a col for REF()
	private int findMinRow() { 
		int indMin = Integer.MAX_VALUE; 
		int col = REFcol();
		for(int i = 0; i < matrix.length; i++) {
			for(int j = col; j < matrix[0].length;) {
				if(matrix[i][j] < indMin && matrix[i][j] != 0) indMin = i;
				break; 
			}
		}
		return indMin;
	}
	
	private void REF() {
		/*int col = 0, row = 0; 

		while(true) {
			
			if(isZeroCol(col)) {
				if(col + 1 < c) c++;
				else break; 
			}
			
			
			while(!isOrderedCol(col)) {
				
				for(int i = col; i < matrix[0].length;) {
					for(int j = row; j < matrix.length; j++) {
						
					}
				}
			}
			
			
			
			
			if(col < c) col++; 
			if(row < r) row++;
		}
		//Arrays.sort(matrix, (a, b) -> Double.compare(b[2], a[2]));
	}
	
	/*
	private void zeroRowEnd(int col) {
		if(isZeroCol(col)) return; 
		int lastZRow = matrix.length - 1; 
		first_loop:
		for(int i = col; i < matrix[0].length;) {
			for(int j = 0; j < matrix.length; j++) {
				if(matrix[j][i] == 0 && isZeroRow(matrix[j], col)) {
					if(j == matrix.length - 1) break first_loop;
					if(j == lastZRow) continue;
					switchRow(j, lastZRow);
					if(r % 2 != 0 && lastZRow == ((r / 2) + 1)) break first_loop;
					if(r % 2 == 0 && lastZRow == r / 2) break first_loop; 
					//if(lastZRow ==  0) break first_loop;
					lastZRow--; 
				}
			}
		}*/
		zeroRowEnd2();
		int rowSub = 0, col = 0,oneIndex = 0, row = 1; 
		double[] subtractor;
		while(!check4()) {
			reduction();
			
			subtractor = matrix[rowSub];
			for(int i = 0; i < subtractor.length;i++) if(subtractor[i] == 1) {
				oneIndex = i; 
				break; 
			}
			
			top:
			for(int i = row; i < matrix.length; i++) {
				for(int j = oneIndex; j < matrix[0].length;) {
					if(matrix[i][j] == 1) {
						matrix[i][j] -= subtractor[j];
					}
					else break top;
					continue top; 
				}
			}
			rowSub++; 
			
		}
		zeroRowEnd2();
		zeroRowOrg();
	}
	
	
	private void detReducer() {
		double[] subtractor; 
		int curSubInd = 0, curCol = 0; 
		int firstNonZeroInd = 0; 
		while(!check8()) {
			subtractor = matrix[curSubInd]; 
			for(int i = 0; i < subtractor.length; i++) if (subtractor[i] != 0 ) {
				firstNonZeroInd = i; 
				break; 
			}
			
			for(int i = curSubInd + 1; i < matrix.length; i++) {
				double multiplier = 1;
				for(int j = firstNonZeroInd; j < matrix[0].length; j++) {
					if (j == firstNonZeroInd) multiplier = matrix[i][j] / subtractor[firstNonZeroInd];
					matrix[i][j] -= subtractor[j] * multiplier; 
				}
			}
			
			
			
			
			curSubInd++;
			
		}
	}
	

	
	private boolean isAllZero(double[] x) {
		boolean retVal = true; 
		for(double i: x) if(i != 0) retVal = false; 
		return retVal; 
	}
	
	private void RREF() {
		//uses the REF function to get a REF matrix. Starts from the last matrix. 
		//if it has a leading one, then it will subtract the entire row from the row above.
		//will do so for each row.
		REF(); 
		int rowSub = matrix.length - 1, col = 0, oneIndex = 0, row = 1; 
		double[] subtractor; 
		while(!check6()) {
			subtractor = matrix[rowSub];
			if(isAllZero(subtractor)) {
				if(rowSub > 0) rowSub--;
				continue; 
			}
			for(int i = 0; i < subtractor.length; i++) if(subtractor[i] == 1) {
				oneIndex = i; 
				break; 
			}
			
			top:
			for(int i = rowSub - 1; i >= 0; i--) {
				double oneUp = matrix[i][oneIndex];
				for(int j = oneIndex; j < matrix[0].length; j++) {
					if(oneUp == 0) continue top;
					
					if(oneUp== 1) {
						matrix[i][j] -= subtractor[j];
						continue; 
					}
					
					matrix[i][j] -= (matrix[rowSub][j] * oneUp);
				}
			}
			if(rowSub > 0) rowSub--;
		}
	}
	
	private void zeroRowEnd2() {
		boolean[][] comp = zeroRowBool();
		int[] fCount = new int[r];
		int f;
		
		//count the number of falses in each row
		for(int i = 0; i < matrix.length; i++) {
			f = 0; 
			for(int j = 0; j < matrix[0].length; j++) {
				if(comp[i][j] == false) f++; 
			}
			fCount[i] = f; 
		}
		
		//bubble sort to sort the boolean array and matrix simultaneously 
		for(int i = 0; i < fCount.length -1; i++) {
			for(int j = 0; j < fCount.length - i - 1; j++) {
				if(fCount[j] < fCount[j+1]) {
					int temp = fCount[j];
					fCount[j] = fCount[j+1];
					fCount[j+1] = temp; 
					switchRow(j, j+1);
				}
			}
		}
	}

	private void switchRow(int r1, int r2) {
		double[] temp1, temp2; 
		temp1 = Arrays.copyOfRange(matrix[r1], 0, c);
		temp2 = Arrays.copyOfRange(matrix[r2], 0, c);
		matrix[r2] = temp1; 
		matrix[r1] = temp2; 
	}
	
	
	private boolean isZeroCol(int col) {
		boolean retVal = false; 
		int zeroCount = 0; 
		first_loop:
		for(int i = col; i < matrix[0].length;) {
			for(int j = 0; j < matrix.length; j++) {
				if(matrix[j][i] == 0) retVal = true;
				else {
					retVal = false; 
					break first_loop;
				}
				if(zeroCount == matrix.length) break first_loop;
				zeroCount++;
			}
		}
		return retVal; 
	}
	
	private boolean isOrderedCol(int col){
		/* starts from the first number in a col of a user chosen matrix and then checks if it is in the right position. If it is, 
		 * then it checks the next number in the col, and so on. */
		boolean retVal = true; 
		double hold = Integer.MAX_VALUE; 
		int counter = 0; 
		while(counter < c) {
			
			first_loop:
			for(int i = col; i < col+1;i++){
				hold = matrix[counter][i];
				for(int j = counter; j < matrix[0].length; j++){
					if(matrix[j][i] > hold) {
						retVal = false;
						break first_loop; 
					}
				}
			}
			counter++;
		
		
		}
		return retVal;
	}
	
	private boolean isZeroRow(double[] row, int place) {
		//checks whether the left of a whole number is all zeros in a row, if its a whole number at the 0 position
		//or  zero, then it is true
		boolean retVal = false;
		if(place - 1 >= 0) {
			for(int i = place - 1; i >= 0; i--) {
				if(row[i] == 0) retVal = true; 
				else {
					retVal = false; 
					break; 
				}
			}
		}
		else  retVal = false;
		if(place == 0 && row[place] == 0) retVal = true;
		return retVal; 
	}
	
	private void zeroRowOrg() {
		//adds all the zero row indices in a matrix to a list, then switches the zero rows
		//with rows at the bottom. NOTE--this is a very inefficient function. Zero rows
		//could be in the right spots, and therefore unnecessary action is taken. 
		List<Integer> retVal = new ArrayList<Integer>();
		int numZeroRows = 0; 
		for(int i = 0; i < matrix.length; i++) {
			boolean zeroRow = false; 
			for(int j = 0; j < matrix[0].length; j++) {
				if(matrix[i][j] == 0) {
					zeroRow = true; 
				}
				else {
					zeroRow = false; 
					break; 
				}
			}
			if(zeroRow == true) {
				retVal.add(i);
			}
		}
		if(!retVal.contains(r-1)) {
			int next = matrix.length - 1; 
			for(int i:retVal) {
				switchRow(i, next);
				next--;
			}
		}
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public double determinant() {
		if(!check7()) throw new IllegalArgumentException("Only square matrices");
		detReducer();
		double total = 1; 
		for(int i = 0; i < matrix.length;) {
			for(int j = 0; j < matrix[0].length; j++) {
				total *= matrix[i][j];
				i++;
			}
		}
		return total; 
	}
	public double determinantRec() {
		if(!check7()) throw new IllegalArgumentException("Bad matrix");
		return determinantRec(subMat);
	}
	
	private static double determinantRec(double[][] m) {
		//base case 1: 1x1 matrix
		if(m.length == 1) return m[0][0];
		//base case 2: 2x2 matrix
		if(m.length == 2) return (m[0][0] * m[1][1]) - 
										 (m[0][1] * m[1][0]);
		double accumulator = 0;
		
		return accumulator;
		
	}
	
	public boolean linInd() {
		if(determinant() != 0) return true; 
		return false;
	}


	private String[][] PoM(){
		if(!check7()) throw new IllegalArgumentException("Bad matrix");
		String[][] retVal = new String[r][c];
		int sign; 
		for(int i = 0; i < retVal.length; i++) {
			if(i % 2 == 0) sign = 0; 
			else sign = 1; 
			for(int j = 0; j < retVal[0].length; j++) {
				if(sign % 2 == 0) retVal[i][j] = "+";
				else retVal[i][j] = "-";
				sign++;
			}
		}
		return retVal; 
	}
	

	public MatrixArrays addition(MatrixArrays a) {

		if(check1(a)) {
			double[][] retVal = new double[r][c];
			for(int i = 0; i < r; i++) {
				for(int j = 0; j < c; j++) {
					retVal[i][j] = matrix[i][j] + a.matrix[i][j];
				}
			}
		return new MatrixArrays(retVal);
		}
		return a;
	}
	
	public MatrixArrays subtraction(MatrixArrays a) {
		if(check1(a)) {
			double[][] retVal = new double[r][c];
			for(int i = 0; i < r; i++) {
				for(int j = 0; j < c; j++) {
					retVal[i][j] = matrix[i][j] - a.matrix[i][j];
				}
			}
		return new MatrixArrays(retVal);
		}
		return a;
	}
	
	public MatrixArrays multiplication(MatrixArrays a) {
		if(check2(a)){
			double[][] retVal = new double[matrix.length][a.matrix[0].length];
			int sum = 0;
			for (int i = 0; i < retVal.length; i++) {
	            for (int j = 0; j < retVal[0].length; j++) { 
	                for (int k = 0; k < matrix[0].length; k++) { 
	                    sum += matrix[i][k] * a.matrix[k][j];
	                }
	                retVal[i][j] = sum;
	                sum = 0;
	            }
	        }
			return new MatrixArrays(retVal);
		}
		return a; 
	}
	
	public MatrixArrays power(int e) {
		if(check3(this)) {
			if(e == 1) return this; 
			MatrixArrays ex = new MatrixArrays(matrix);
			double[][] ret = new double[r][c];
			for(int i = 0; i < e - 1; i++) {
				ret = (ex.multiplication(this)).matrix;
				ex = new MatrixArrays(ret);
			}
			return ex;
		}
		return this;
	}
	
	/*
	public MatrixArrays REF() {

		if(check4()) return this;
		if(check5()) return this; 
		//find row with smallest number in first entry 
		while(!check4()) {
			//reduces a row with all zeros except the last entry to 1 
			int zeroCount = 0; 
			for(int i = 0; i < matrix.length; i++) {
				for(int j = 0; j < matrix[0].length; j++) {
					if(zeroCount == (c - 1)) {
						matrix[i][j] = matrix[i][j] / matrix[i][j];
					}
					if(matrix[i][j] == 0) zeroCount++;
				}
			}
		
		}
		return null;
	}*/
	
	public void transpose() {
		double[][] temp = new double[c][r];
		for(int i = 0; i < matrix.length; i++ ) {
			for(int j = 0; j < matrix[0].length; j++) {
				temp[j][i] = matrix[i][j];
			}
		}
		matrix = temp; 
		int tem = r; 
		int tem1 = c; 
		r = tem1; 
		c = tem; 
		
	}
	
	public void reduction() {
		top:
		for(double[] a:matrix) {
			int hold = -1;
			for(int i = 0; i < a.length; i++) if(a[i] != 0) {
				if(a[i] == 1) continue top;
				hold = i;
				break;
			}
			if(hold != -1) {
				double v = a[hold];
				for(int i = 0; i < a.length; i++) a[i] = a[i] / v;
			}
		}
	}
	
	
	public void prettyPrint() {
		for(double[] a: matrix) {
			System.out.println(Arrays.toString(a).replace("[", "").replace("]", "").replace(",", ""));
		}
	}
	
	public boolean[][] zeroRowBool(){
		boolean[][] retVal = new boolean[r][c];
		for(int i = 0; i < retVal.length; i++) {
			for(int j = 0; j < retVal[0].length; j++) {
				retVal[i][j] = isZeroRow(matrix[i],j);
			}
		}
		return retVal; 
	}
	
	public static void main(String[] args) {
		/*double[][] m1 = {{1,2,3},
				      {3,4,5},
					  {1,2,3}};*/
		double[][] m2 = {{1,2,3},
				      {0,-3,-4},
					  {0,1,-8}};
		double[][] m3 = {{0,0,0},
				      {0,0,5},
				      {2,0,1},
				      {3,0,1}};
		double[][] m4 = {{0,0,1,2},
						{0,1,2,1},
						{1,2,3,1}};
		double[][] m5 = {{1,2,3},
						 {2,1,2},
						 {3,0,1}};
							
		
		//MatrixArrays temp = new MatrixArrays(m3);
		//System.out.println(temp.isZeroRow(temp.matrix[0], 1));
		//temp.sortM(0);
		//temp.zeroRowEnd2();
		//temp.prettyPrint();
		//System.out.println();
		
		
		//MatrixArrays temp2 = new MatrixArrays(m4);
		//temp2.altREF();
		//temp2.prettyPrint();
		
		MatrixArrays temp3 = new MatrixArrays(m2);
		temp3.detReducer();
		temp3.prettyPrint();
		System.out.println(temp3.determinant());
		
		
	}
	
	
	
	
	
}