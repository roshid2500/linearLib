package matrix;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/*
 * This class is an upgrade to MatrixArrays, which was not efficient in terms of time and space complexity. 
 */
public class Matrix {
	//class variables 
	private double[][] matrix; 
	private int rows, cols; 
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/** These methods serve to check the given matrix in order to determine whether certain operations can be performed */
	
	/*
	 * Checks if argument is the same size as the current object 
	 */
	private boolean sameSize(Matrix a) {
		return a.getRows() == rows && a.getCols() == cols;
	}
	
	/*
	 * Checks if the argument's rows equals the cols of the current object
	 */
	private boolean multipliable(Matrix a) {
		return cols == a.getRows();
	}
	
	/*
	 * Checks if a matrix is a square matrix
	 */
	private boolean isSquare() {
		return cols == rows; 
	}
	
	/*
	 * check if matrix is already row reduced
	 * This method keeps track of the number of zeros until a non-zero number is reached 
	 * and the index in which the next non-zero number should appear in order to tell 
	 * whether the matrix is already in reduced form. If a non-zero number appears 
	 * and its j-position is < the index of the next non-zero or the number of zeros
	 * counted thus far is <= the last row's total zeros before hitting a non-zero, then
	 * then false is returned. Else true. 
	 */
	private boolean isReduced() {
		int nonZero = 0, numZeros = 0; 
		boolean retVal = true; 
		
		top:
		for(int i = 0; i < rows; i++) {
			int zeroCount = 0; 
			for(int j = 0; j < cols; j++) {
				if(matrix[i][j] == 0) {
					zeroCount++; 
					continue; 
				}
				if(matrix[i][j] != 0 && j < nonZero) {
					retVal = false; 
					break top; 
				}
				if(matrix[i][j] != 0 && (j >= nonZero || j == 0)) {
					nonZero = j + 1; 
					retVal = true; 
					if(j <= numZeros)
						retVal = false;
					break; 
				}
			}			
			numZeros = (zeroCount > numZeros) ? zeroCount: numZeros; 
		}
		
		return retVal; 
	}
	
	/*
	 * Checks if the matrix is in RREF 
	 */
	private boolean isAbsReduced() {
		if(!isReduced())
			return false;
		boolean res = true; 
		
		top:
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				if(matrix[i][j] == 0)
					continue; 
				if(matrix[i][j] != 1) {
					res = false; 
					break top; 
				}
				if(matrix[i][j] == 1) {
					for(int k = 0; k < rows; k++) {
						if(k == i)
							continue;
						if(matrix[k][j] != 0) {
							res = false;
							break top; 
						}
					}
				}
				continue top; 
			}
		}
		return res; 
	}
	
	/*
	 * Checks if row is a zero row
	 */
	private boolean isZeroRow(double[] row) {
		boolean res = true; 
		for(int i = 0; i < row.length; i++) {
			if(row[i] != 0) {
				res = false;
				break; 
			}
		}
		return res; 
	}
	 
	/*
	 * Returns a boolean array that depends on the number of zeros before the 
	 * first non-zero number. All indices before the non-zero number are true
	 * and the first index having a non-zero number is true as well 
	 */
	private boolean[][] zeroBoolArr(){
		boolean[][] retVal = new boolean[rows][cols];
		for(int i = 0; i < retVal.length; i++) {
			isZeroRow(matrix[i], retVal[i]);
		}
		return retVal; 
	}
	
	/*
	 * Helper method to zeroBoolArr()
	 */
	private void isZeroRow(double[] row, boolean[] val) {
		int wholeNum = 0; 
		for(int i = 0; i < row.length; i++) {
			if(row[i] == 0)
				val[i] = true; 
			if(row[i] != 0) {
				val[i] = true;
				break; 
			}
		}
	}
	
	/*
	 * Organizes the matrix from greatest rows to least rows 
	 */
	private void zeroRowOrg() {
		boolean[][] comp = zeroBoolArr();
		int[] fCount = new int[rows];
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
	
	/*
	 * Swaps rows in arrays, must create two temps b/c 
	 * temp1 = temp2 means they're referring to the exact same array
	 */
	private void switchRow(int r1, int r2) {
		double[] temp1, temp2; 
		temp1 = Arrays.copyOfRange(matrix[r1], 0, cols);
		temp2 = Arrays.copyOfRange(matrix[r2], 0, cols);
		matrix[r2] = temp1; 
		matrix[r1] = temp2; 
	}
	
	/*
	 * Reduces all rows so the leading non-zero is 1 
	 */
	private void rowReducer() {
		for(double[] a: matrix) {
			double divider = -1; 
			for(int i = 0; i < a.length; i++) {
				if(a[i] == 0)
					continue; 
				
//				if(a[i] == 1 && divider != -1)
//					break; 
				
				if(divider == -1) 
					divider = a[i];
					
				a[i] = Math.round(a[i] / divider * 100.00) / 100.00; 	
			}
		}
	}
	
	/*
	 * Simply uses BigDecimal to 
	 * round a number to a specified number of digits after the decimal pt
	 */
	private static double round(double v, int p) {
	    BigDecimal bd = BigDecimal.valueOf(v);
	    bd = bd.setScale(p, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	
	private void zeroRowSwitch() {
		boolean[] isZero = new boolean[rows];
		for(int i = 0; i < rows; i++) {
			int zeroes = 0; 
			for(int j = 0; j < cols; j++) {
				if(matrix[i][j] == 0)
					zeroes++; 
			}
			if(zeroes == cols)
				isZero[i] = true; 
		}
		
		int isNotZero = isZero.length - 1; 
		int i = 0; 
		while(true) {
			if(i >= isNotZero)
				break; 
			if(isZero[i]) {
				if(isZero[isNotZero]) {
					isNotZero--; 
					continue; 
				}
				switchRow(i,isNotZero);
			}
			i++; 
		}
	}
	/*
	 * Return an eigenvector
	 */
//	private static double[] getVector(double ev, double[][] mat){
//		double[] retVal = new double[mat[0].length];
//		for(int i = 0; i < mat.length;i++) {
//			mat[i][i] = -ev + mat[i][i]; 
//		}
//		
//		int counter = 0; 
//		while(counter != retVal.length) {
//			
//		}
//		
//	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/** These methods are the operations which users will have access to */
	
	/*
	 * This constructor creates a matrix r x c and takes in user input for the values. User input is taken in one value a time going 
	 * row by row. Assume user gives correct input. 
	 */
	public Matrix(int r, int c){
		if(r <= 0 && c <= 0)
			throw new IllegalArgumentException("Invalid Rows/Cols");
		rows = r; 
		cols = c; 
		matrix = new double[rows][cols];
		
		int rowCheck = 0;
		while(rowCheck != rows) {
			Scanner g = new Scanner(System.in); 
			for(int i = 0; i < cols; i++) {
				System.out.println("Enter in the value for row " + rowCheck + ", column " + i);
				matrix[rowCheck][i] = g.nextDouble();
			}
			rowCheck++;
		}
	}
	
	
	/*
	 * This constructor takes in a matrix directly 
	 */
	public Matrix(double[][] m) {
		if(m == null)
			throw new IllegalArgumentException("Invalid matrix argument");
		rows = m.length;
		cols = m[0].length; 
		matrix = m; 
	}
	
	
	/*
	 * Prints the matrix
	 */
	public void prettyPrint() {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols;j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	/*
	 * Adds two matrices, result is stored in the current object 
	 */
	public void add(Matrix b) {
		if(!sameSize(b))
			throw new IllegalArgumentException("These matrices sizes differ, hence they cannot be added together");
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				matrix[i][j] += b.getValue(i,j);
			}
		}
		
	}
	
	/*
	 * subtracts two matrices, result is stored in the current object 
	 */
	public void subtract(Matrix b) {
		if(!sameSize(b))
			throw new IllegalArgumentException("These matrices sizes differ, hence this matrix cannot be subtracted by b");
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				matrix[i][j] -= b.getValue(i,j);
			}
		}
		
	}
	
	
	/*
	 * Multiplies two matrices, result is stored in the current object. Note, this method updates the current object to a new r x c 
	 */
	public void multiply(Matrix b) {
		if(!multipliable(b))
			throw new IllegalArgumentException("Cannot multiply these two matrices");
		double[][] newMat = new double[rows][b.getCols()];
		
		for(int i = 0; i < newMat.length; i++) {
			for(int j = 0; j < newMat[0].length; j++) {
				for(int k = 0; k < cols; k++) {
					newMat[i][j] += matrix[i][k] * b.getValue(k,j);
				}
			}
		}
		rows = newMat.length; 
		cols = newMat[0].length; 
		matrix = newMat; 
	}
	
	
	/*
	 * Raises a square matrix to a power 
	 */
	public void power(int p) {
		if(!isSquare())
			throw new IllegalArgumentException("Must be a square matrix to be raised to a power");
		if(p == 0 || p == 1) 
			return; 
		Matrix result = new Matrix(getMatrix());
		for(int i = 0; i < p-1; i++) {
			multiply(result);
		}
	}
	
	/*
	 * This method transposes the matrix. If it's a square matrix, then there is no need to create a new matrix. 
	 * Otherwise, new matrix needed.
	 */
	public void transpose() {
		if(rows == cols) {
			for(int i = 0; i<matrix.length; i++){
				for(int j = i; j<matrix[0].length; j++){
					double temp = 0;
					temp = matrix[i][j];
					matrix[i][j] = matrix[j][i];
					matrix[j][i] = temp;
				}
			}
		}
		else {
			double[][] newMat = new double[cols][rows];
			for(int i = 0; i < matrix.length; i++) {
				for(int j = 0; j < matrix[0].length; j++) {
					newMat[j][i] = matrix[i][j]; 
				}
			}
			int temp = rows; 
			rows = cols; 
			cols = temp; 
			matrix = newMat; 
		}
	}
	
	/*
	 * Finds the REF of a matrix 
	 */
	public void REF() {
		zeroRowOrg(); 
		int rowSub = 0, oneInd = 0, row = 1; 
		double[] subtractor; 
		while(!isReduced()) { 
			subtractor = matrix[rowSub];
			for(int i = 0; i < subtractor.length; i++) {
				if(subtractor[i] != 0) {
					oneInd = i; 
					break; 
				}
			}
			
			double divider = 1; 
			top:
				for(int i = row; i < matrix.length; i++) {
					for(int j = oneInd; j < matrix[0].length; j++) {
						if(matrix[i][j] != 0 && j == oneInd)
							divider = matrix[i][j] / subtractor[j]; 
						else if(matrix[i][j] == 0 && j == oneInd)
							break top; 
						matrix[i][j] = round(matrix[i][j] - subtractor[j] * divider, 2); 
					}
				}
			zeroRowSwitch();
//			if(rowSub == rows-1)
//				break; 
			rowSub++; 
			row++;
		}
	}
	
	/*
	 * Finds the RREF of a matrix 
	 */
	public void RREF() {
		REF(); 
		if(isAbsReduced())
			return; 
		int rowSub = matrix.length - 1, oneInd = 0; 
		double[] subtractor; 
		rowReducer();
		while(rowSub != 0) {
			subtractor = matrix[rowSub];
			if(isZeroRow(subtractor)) { 
				rowSub--;
				continue; 
			}
			
			for(int i = subtractor.length - 1; i >= 0; i--) {
				if(subtractor[i] == 1) {
					oneInd = i; 
					break; 
				}
			}
			
			top:
			for(int i = rowSub - 1; i >= 0; i--) {
				double oneUp = matrix[i][oneInd];
				for(int j = oneInd; j < matrix[0].length; j++) {
					if(oneUp == 0)
						continue top; 
					if(oneUp == 1) { 
						matrix[i][j] -= subtractor[j]; 
						continue; 
					}
					matrix[i][j] = round(matrix[i][j] - matrix[rowSub][j] * oneUp,2); 
				}
			}
			 
			rowSub--; 
		}
	}
	
	
	/*
	 * Find determinant of a square matrix 
	 */
	public double determinant() {
		if(!isSquare())
			throw new IllegalArgumentException("Must be a square matrix");
		REF();
		
		double retVal = 1; 

		for(int i = 0; i < rows; i++) {
			retVal *= matrix[i][i]; 
		}
		retVal = round(retVal, 2);
		return retVal + 0; 
	}
	
	
	/*
	 * Find the eigenvalues of a matrix
	 */
	public Set<Double> eigenValues(){
		if(!isSquare())
			throw new IllegalArgumentException("Invalid matrix");
		REF(); 
		Set<Double> ret = new TreeSet<Double>(); 
		for(int i = 0; i < rows; i++) {
			ret.add(matrix[i][i]);
		}
		return ret; 
	}
	
	
//	/*
//	 * Find the eigenvectors of a matrix
//	 */
//	public double[][] eigenVectors(){
//		if(!isSquare())
//			throw new IllegalArgumentException("Invalid matrix");
//		Set<Double> eigenvals = eigenValues();
//		double[][] retVal = new double[eigenvals.size()][cols];
//		
//		int row = 0;
//		for(double a:eigenvals) {
//			retVal[row] = getVector(a, matrix);
//			row++;
//		}
//		
//		
//		return retVal; 
//	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/** These methods return information about the matrix */
	public int getRows() {
		return rows; 
	}
	
	public int getCols() {
		return cols; 
	}
	
	public double getValue(int r, int c) {
		return matrix[r][c]; 
	}
	
	public double[][] getMatrix(){
		return matrix; 
	}
	
	
	
	
	
	
	
	
	
	

	
	
	public static void main(String[] args) {
//		Matrix a = new Matrix(new double[][] {{1,5,2}, {1, 1, 0 }, {5, 2, 1}});
//		a.prettyPrint(); 
//		System.out.println(a.determinant());
//		System.out.println(a.eigenValues().toString());
//		
//		double[][] cc = new double[0][0]; 
//		System.out.println(cc.length + cc[0].length);
		
		Matrix A = new Matrix(new double[][] {{-5,2,5,3}, {-5,10,21,12}, {-8,9,0,1},{0,0,1,2}});
		for(double a: A.eigenValues()) {
			System.out.println(a);
		}
		
	
		
	}
}
