package matrix;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/*
 * Because there are many types of matrices and they may be organized in many different ways, code involving 
 * matrices must tend to many different cases. And hence, there is a need for testing a variety of matrices
 * for the numerous operations that exist. 
 */
class MatrixTest {

	/*
	 * Tests the matrix constructor which builds a matrix using the Java Scanner
	 * USE INPUTS 1.0, 2.0, 0.0, 2.0 
	 */
	
	@Test
	void BuildMatrix1() {
		Matrix a = new Matrix(2,2);
		double[][] ac = new double[][] {{1,2},{0,2}};
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 2; j++) {
				assertEquals(a.getMatrix()[i][j], ac[i][j]);
			}
		}
	}

	/*
	 * Tests the matrix constructor which builds a matrix using the Java Scanner
	 * USE INPUTS 1,2,3,4,0,2,1,0
	 */
	
	@Test 
	void BuildMatrix2() {
		Matrix a = new Matrix(2,4);
		double[][] ac = new double[][] {{1,2,3,4},{0,2,1,0}};
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 2; j++) {
				assertEquals(a.getMatrix()[i][j], ac[i][j]);
			}
		}
	}
	
	
	/*
	 * Tests the second Matrix constructor, which build a matrix from a user-made double[][]
	 */
	@Test
	void BuildMatrix3() {
		double[][] a = new double[][] {{1,5,3,2,423},{100,2,3,5,2}};
		Matrix ab = new Matrix(a); 
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 2; j++) {
				assertEquals(ab.getMatrix()[i][j], a[i][j]);
			}
		}
	}
	
	
	/*
	 * Tests whether an exception is thrown given a bad input for rows or columns is given
	 */
	
	@Test
	void TestScannerConstructor1() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Matrix a = new Matrix(0, 0);
		});
	}
	
	/*
	 * Tests whether an exception is thrown given a bad input for a matrix
	 */
	
	@Test
	void TestScannerConstructor2() {
		double[][] a = null;
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Matrix b = new Matrix(a);
		});
	}
	
	
	
	/*
	 * Tests whether adding two matrices results in the correct matrix 
	 */
	//
	@Test
	void addMatrices1() {
		double[][] a = {{1,2}, {3,4}}; 
		double[][] b = {{5,5}, {4,3}}; 
		double[][] res = {{6,7}, {7,7}};
		
		Matrix A = new Matrix(a); 
		Matrix B = new Matrix(b); 
		
		A.add(B); 
		
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 2; j++) {
				assertEquals(A.getMatrix()[i][j], res[i][j]);
			}
		}
	}
	
	
	/*
	 * Tests whether adding two matrices results in the correct matrix 
	 */
	
	@Test
	void addMatrices2() {
		double[][] a = {{55,10,77}, {43,3,42}, {6,7,8}}; 
		double[][] b = {{543,43,645}, {523,54,96}, {0,23,5}}; 
		double[][] res = {{598,53,722}, {566,57,138}, {6,30,13}};
		
		Matrix A = new Matrix(a); 
		Matrix B = new Matrix(b); 
		
		A.add(B); 
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				assertEquals(A.getMatrix()[i][j], res[i][j]);
			}
		}
	}
	
	
	/*
	 * Returns exception given an invalid matrix in which addition cannot be performed 
	 */
	 
	@Test
	void additionReturnsException1() {
		double[][] a = {{1,2,3}, {3,4,5}}; 
		double[][] b = {{5,5}, {4,3}}; 
		double[][] res = {{6,7}, {7,7}};
		
		Matrix A = new Matrix(a); 
		Matrix B = new Matrix(b); 
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			A.add(B);
		}); 
	}
	
	
	/*
	 * Tests whether subtracting two matrices results in the correct matrix 
	 */
	
	@Test
	void subtractMatrices1() {
		double[][] a = {{1,2}, {3,4}}; 
		double[][] b = {{5,5}, {4,3}}; 
		double[][] res = {{-4,-3}, {-1,1}};
		
		Matrix A = new Matrix(a); 
		Matrix B = new Matrix(b); 
		
		A.subtract(B); 
		
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 2; j++) {
				assertEquals(A.getMatrix()[i][j], res[i][j]);
			}
		}
	}
	
	
	/*
	 * Tests whether subtracting two matrices results in the correct matrix 
	 */
	
	@Test
	void subtractMatrices2() {
		double[][] a = {{55,10,77}, {43,3,42}, {6,7,8}}; 
		double[][] b = {{543,43,645}, {523,54,96}, {0,23,5}}; 
		double[][] res = {{-488,-33,-568}, {-480,-51,-54}, {6,-16,3}};
		
		Matrix A = new Matrix(a); 
		Matrix B = new Matrix(b); 
		
		A.subtract(B); 
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				assertEquals(A.getMatrix()[i][j], res[i][j]);
			}
		}
	}
	
	
	/*
	 * Returns exception given an invalid matrix in which subtraction cannot be performed 
	 */
	 
	@Test
	void SubtractionReturnsException1() {
		double[][] a = {{1,2,3}, {3,4,5}}; 
		double[][] b = {{5,5}, {4,3}}; 
		double[][] res = {{6,7}, {7,7}};
		
		Matrix A = new Matrix(a); 
		Matrix B = new Matrix(b); 
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			A.subtract(B);
		}); 
		
		
	}
	
	
	/*
	 * Tests whether multiplying two matrices results in the correct matrix 
	 */
	
	@Test
	void multiplyMatrices1() {
		double[][] a = {{1,2,5}, {3,2,4}}; 
		double[][] b = {{3,3,3},{6,6,6},{5,5,5}};
		double[][] res = {{40,40,40}, {41,41,41}}; 
		
		Matrix A = new Matrix(a);
		Matrix B = new Matrix(b);
		
		A.multiply(B);
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 3; j++) {
				assertEquals(A.getMatrix()[i][j], res[i][j]);
			}
		}
	}
	
	
	/*
	 * Tests whether multiplying two matrices results in the correct matrix 
	 */
	
	@Test
	void multiplyMatrices2() {
		double[][] a = {{1,2,5}, {3,2,4}, {4,5,6}}; 
		double[][] b = {{3,3,3},{6,6,6},{5,5,5}};
		double[][] res = {{40,40,40}, {41,41,41}, {72,72,72}}; 
		
		Matrix A = new Matrix(a);
		Matrix B = new Matrix(b);
		
		A.multiply(B);
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 3; j++) {
				assertEquals(A.getMatrix()[i][j], res[i][j]);
			}
		}
	}
	
	
	/*
	 * Returns exception given an invalid matrix in which multiplying cannot be performed 
	 */
	 
	@Test
	void multiplicationReturnsException1() {
		double[][] a = {{1,2,5}, {3,2,4}, {4,5,6}}; 
		double[][] b = {{3,3,3},{6,6,6}};
		double[][] res = {{40,40,40}, {41,41,41}, {72,72,72}}; 
		
		Matrix A = new Matrix(a);
		Matrix B = new Matrix(b);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			A.multiply(B);
		}); 
		
	}
	
	/*
	 * Tests whether the power method returns correct output 
	 */
	
	@Test
	void powerMatrices() {
		Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6},{7,8,9}});
		double[][] res = {{30,36,42},{66,81,96},{102,126,150}};
		
		A.power(2);
		for(int i = 0; i < res.length; i++) {
			for(int j = 0; j < res[0].length; j++) {
				assertEquals(A.getValue(i,j), res[i][j]);
			}
		}
	}
	
	
	/*
	 * Tests whether exception is caught for power method 
	 */
	
	@Test
	void powerReturnsException() {
		Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			A.power(2);
		}); 
		
	}
	
	
	/*
	 * Matrix is transposed in-place into correct position 
	 */
	
	@Test
	void transposeMatrix() {
		Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6},{7,8,9}});
		double[][] res = {{1,4,7},{2,5,8},{3,6,9}};
		
		A.transpose(); 
		for(int i = 0; i < res.length; i++) {
			for(int j = 0; j < res[0].length; j++) {
				assertEquals(A.getValue(i,j), res[i][j]);
			}
		}
	}
	
	
	/*
	 * Matrix is transposed by creating a new array into correct position 
	 */
	
	@Test
	void transposeMatrix2() {
		Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6}});
		double[][] res = {{1,4}, {2,5}, {3,6}};
		
		A.transpose(); 
		for(int i = 0; i < res.length; i++) {
			for(int j = 0; j < res[0].length; j++) {
				assertEquals(A.getValue(i,j), res[i][j]);
			}
		}
	}
	
	/*
	 * Tests REF() function for various matrices 
	 */
	
	@Test
	void REFtest1() {
		Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6},{7,8,9}});
		double[][] res = {{1,2,3},{0,-3,-6},{0,0,0}}; 
		
		A.REF(); 
		
		for(int i = 0; i < res.length; i++) {
			for(int j = 0; j < res[0].length; j++) {
				assertEquals(A.getValue(i,j), res[i][j]);
			}
		}
	}
	
	
	/*
	 * Tests REF() function for various matrices 
	 */
	
	@Test
	void REFtest2() {
		Matrix A = new Matrix(new double[][] {{0,1,2,3},{0,4,5,6},{0,7,8,9}});
		double[][] res = {{0,1,2,3},{0,0,-3,-6},{0,0,0,0}}; 
		
		A.REF(); 
		
		for(int i = 0; i < res.length; i++) {
			for(int j = 0; j < res[0].length; j++) {
				assertEquals(A.getValue(i,j), res[i][j]);
			}
		}
	}
	
	
	/*
	 * Tests REF() function for various matrices 
	 */
	
	@Test
	void REFtest3() {
		Matrix A = new Matrix(new double[][] {{5,6},{10,12},{4,2}});
		double[][] res = {{5,6},{0,-2.8},{0,0}};
		
		A.REF(); 
		
		for(int i = 0; i < res.length; i++) {
			for(int j = 0; j < res[0].length; j++) {
				assertEquals(A.getValue(i,j), res[i][j]);
			}
		}
	}
	
	
	
	/*
	 * Tests REF() function for various matrices 
	 */
	//
	@Test
	void REFtest4() {
		Matrix A = new Matrix(new double[][] {{1,5,3,6,3},{421,534,542,543,222},{-535,-43,-54,-87,0}});
		double[][] res = {{1,5,3,6,3},{0,-1571,-721,-1983,-1041},{0,0,343.06,-199.25,-139.06}};
		
		A.REF(); 
		
		for(int i = 0; i < res.length; i++) {
			for(int j = 0; j < res[0].length; j++) {
				assertEquals(A.getValue(i,j), res[i][j]);
			}
		}
	}
	
	
	/*
	 * Tests REF() function for various matrices 
	 */
	
	@Test
	void REFtest5() {
		Matrix A = new Matrix(new double[][] {{0,0,0,0},{1,2,3,4},{4,3,2,1},{0,0,0,0}});
		double[][] res = {{1,2,3,4},{0,-5,-10,-15},{0,0,0,0}, {0,0,0,0}};
		
		A.REF(); 
		
		for(int i = 0; i < res.length; i++) {
			for(int j = 0; j < res[0].length; j++) {
				assertEquals(A.getValue(i,j), res[i][j]);
			}
		}
	}
	
	
	/*
	 * Tests RREF() function for various matrices 
	 */
	//
	@Test
	void RREFtest1() {
		Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6},{7,8,9}});
		double[][] res = {{1,0,-1},{0,1,2},{0,0,0}}; 
		
		A.RREF(); 
		
		for(int i = 0; i < res.length; i++) {
			for(int j = 0; j < res[0].length; j++) {
				assertEquals(A.getValue(i,j), res[i][j]);
			}
		}
	}
	
	/*
	 * Tests RREF() function for various matrices 
	 */
	//
	@Test
	void RREFtest2() {
		Matrix A = new Matrix(new double[][] {{0,1,2,3},{0,4,5,6},{0,7,8,9}});
		double[][] res = {{0,1,0,-1},{0,0,1,2},{0,0,0,0}}; 
		
		A.RREF(); 
		
		for(int i = 0; i < res.length; i++) {
			for(int j = 0; j < res[0].length; j++) {
				assertEquals(A.getValue(i,j), res[i][j]);
			}
		}
	}
	
	/*
	 * Tests RREF() function for various matrices 
	 */
	//
	@Test
	void RREFtest3() {
		Matrix A = new Matrix(new double[][] {{5,6},{10,12},{4,2}});
		double[][] res = {{1,0},{0,1},{0,0}};
		
		A.RREF(); 
		
		for(int i = 0; i < res.length; i++) {
			for(int j = 0; j < res[0].length; j++) {
				assertEquals(A.getValue(i,j), res[i][j]);
			}
		}
	}
	
	
	/*
	 * Tests RREF() function for various matrices 
	 */
	
	@Test
	void RREFtest4() {
		Matrix A = new Matrix(new double[][] {{1,5,3,6,3},{421,534,542,543,222},{-535,-43,-54,-87,0}});
		double[][] res = {{1,0,0,0.09,-0.02},{0,1,0,1.53,0.85},{0,0,1,-0.58,-0.41}};
		
		A.RREF(); 
		
		for(int i = 0; i < res.length; i++) {
			for(int j = 0; j < res[0].length; j++) {
				assertEquals(A.getValue(i,j), res[i][j]);
			}
		}
	}
	
	
	/*
	 * Tests RREF() function for various matrices 
	 */
	
	@Test
	void RREFtest5() {
		Matrix A = new Matrix(new double[][] {{0,0,0,0},{1,2,3,4},{4,3,2,1},{0,0,0,0}});
		double[][] res = {{1,0,-1,-2},{0,1,2,3},{0,0,0,0}, {0,0,0,0}};
		
		A.RREF(); 
		
		for(int i = 0; i < res.length; i++) {
			for(int j = 0; j < res[0].length; j++) {
				assertEquals(A.getValue(i,j), res[i][j]);
			}
		}
	}
	
	
	/*
	 * Tests whether determinant returned works for various matrices 
	 */
	
	@Test
	void determinantTest1() {
		Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6},{7,8,9}});
		assertEquals(A.determinant(), 0.0);
	}
	
	/*
	 * Tests whether determinant returned works for various matrices 
	 */
	//
	@Test
	void determinantTest2() {
		Matrix A = new Matrix(new double[][] {{153,234,124},{90,34,546},{934,802,932}});
		assertEquals(A.determinant(), 4.256709291E7);
	}
	
	
	/*
	 * Tests whether determinant returned works for various matrices 
	 */
	
	@Test
	void determinantTest3() {
		Matrix A = new Matrix(new double[][] {{-5,2,5,3}, {-5,10,21,12}, {-8,9,0,1},{0,0,1,2}});
		assertEquals(A.determinant(), 1152.48);
	}
	
	
	
	
	/*
	 * Tests whether determinant returns exception
	 */
	//
	@Test
	void determinantReturnsException() {
		Matrix A = new Matrix(new double[][] {{1,2,3},{3,4,5}}); 
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			A.determinant();
		}); 
	}
	
	
	/*
	 * Tests whether correct eigenvalues are returns 
	 */
	 
	@Test
	void eigenvaluesTest1() {
		Matrix A = new Matrix(new double[][] {{1,2,3},{4,5,6},{7,8,9}});
		Set<Double> s = A.eigenValues(); 
		List<Double> res = Arrays.asList(-3.0,0.0,1.0);
		
		int i = 0; 
		for(double a:s) {
			assertEquals(a, res.get(i));
			i++;
		}
	}
	
	/*
	 * Tests whether correct eigenvalues are returns 
	 */
	 
	@Test
	void eigenvaluesTest2() {
		Matrix A = new Matrix(new double[][] {{153,234,124},{90,34,546},{934,802,932}});
		Set<Double> s = A.eigenValues(); 
		List<Double> res = Arrays.asList(-2684.19,-103.65,153.0);
		
		int i = 0; 
		for(double a:s) {
			assertEquals(a, res.get(i));
			i++;
		}
	}
	
	/*
	 * Tests whether correct eigenvalues are returns 
	 */
	// 
	@Test
	void eigenvaluesTest3() {
		Matrix A = new Matrix(new double[][] {{-5,2,5,3}, {-5,10,21,12}, {-8,9,0,1},{0,0,1,2}});
		Set<Double> s = A.eigenValues(); 
		List<Double> res = Arrays.asList(-19.6,-5.0,1.47,8.0);
		
		int i = 0; 
		for(double a:s) {
			assertEquals(a, res.get(i));
			i++;
		}
	}
	
	
	
	/*
	 * Tests whether bad matrix returns an exception 
	 */
	 
	@Test
	void eigenvaluesReturnsException() {
		Matrix A = new Matrix(new double[][] {{1,2,3},{3,4,5}}); 
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			A.eigenValues();
		}); 
	}
	
	
	
	
	
}
