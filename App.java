//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package P2;

import P1.ExceptionHandled;
import P1.Matrix;
import P1.Stats;
import P2.Eigenvector;
import P2.TSP;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class App {
    static Matrix meanVector;
    static Matrix covariance;

    public App() {
    }

    public static void main(String[] args) throws Exception {
        Matrix m1 = new Matrix("src/main/resources/dataP2.txt", 1);
        Stats s1 = m1.getStats();
        double[][] covarianceData = getCovarianceMatrix(s1).getData();
        Matrix covariance = new Matrix(covarianceData);
        System.out.println(covariance.toString());
        System.out.println(covariance);
        getMeanVector(s1);
        double trace = getTrace(new Matrix(covarianceData));
        System.out.println("Determinant: " + getDeterminant(new Matrix(covarianceData)));
        double[] tempQuadRoots = new double[]{1.0D, -0.7816816527823081D, 0.02975540054243308D};
        double[] eigenvalues = getEigenValues(tempQuadRoots);
        Eigenvector E = new Eigenvector(new Matrix(covarianceData));
        E.Leverriers(getCovarianceMatrix(s1));
        System.out.println("\nPower Method for covariance");
        System.out.println("This one");
        System.out.println(E.powerMethod(new Matrix(covarianceData)));
    }

    private static Matrix getMeanVector(Stats s) {
        Matrix mean = new Matrix(s.getMeanVectorOne().getData());
        meanVector = mean;
        return mean;
    }

    private static void getUnitLength(Matrix n) throws Exception {
        double[][] holder = n.getData();
        double[][] temp = new double[n.getColumns()][n.getRows()];
        double[][] product = n.mult(n).getData();

        for(int p = 0; p < n.getRows(); ++p) {
            for(int blah = 0; blah < n.getColumns(); ++blah) {
                temp[p][blah] = Math.sqrt(product[p][blah]);
            }
        }

        Matrix var7 = new Matrix(temp);
        Matrix var8 = new Matrix(holder);
        Matrix out = var8.mult(new Matrix(var7.getInverse(var7.getData())));
        System.out.println("--------------------------");
        out.toString();
        System.out.println("--------------------------");
    }

    private static double[] getEigenValues(double[] values) {
        return quadratic(values[0], values[1], values[2]);
    }

    private static double getDeterminant(Matrix covariance) throws ExceptionHandled {
        return covariance.determinant(covariance.getData());
    }

    private static double getTrace(Matrix covariance) {
        double sum = 0.0D;

        for(int i = 0; i < covariance.getRows(); ++i) {
            for(int j = 0; j < covariance.getColumns(); ++j) {
                if(i == j) {
                    sum += covariance.getData()[i][j];
                }
            }
        }

        System.out.println("Trace of Covariance: " + sum);
        return sum;
    }

    private static Matrix getCovarianceMatrix(Stats p1) throws ExceptionHandled {
        Matrix cov = new Matrix(p1.getCovarianceMatrix(p1.getMeanVectorOne(), p1.getClassOne()).getData());
        return cov;
    }

    private static ArrayList<TSP> readTSP(String filename) {
        ArrayList tspData = new ArrayList();
        ArrayList lines = readData(filename);
        int e = 0;
        double sum1x = 0.0D;
        double sum2x = 0.0D;
        double sum1y = 0.0D;
        double sum2y = 0.0D;

        for(Iterator var12 = lines.iterator(); var12.hasNext(); ++e) {
            String ln = (String)var12.next();
            String[] x = ln.split("\\s+");
            int m = 0;
            double[][] temp = new double[2][1];
            double[][] dataTemp = new double[2][1];
            String[] var18 = x;
            int var19 = x.length;

            for(int var20 = 0; var20 < var19; ++var20) {
                String element = var18[var20];
                String token = element.trim();
                System.out.println("Element: " + element);
                System.out.println("Token: " + token);
                double value;
                if(m == 0) {
                    value = Double.valueOf(token).doubleValue();
                    dataTemp[0][0] = value;
                    sum1x += value;
                } else if(m == 1) {
                    value = Double.valueOf(token).doubleValue();
                    dataTemp[1][0] = value;
                    sum1y += value;
                } else if(m == 2) {
                    tspData.add(new TSP(dataTemp[0][0], dataTemp[1][0], token));
                }

                ++m;
            }
        }

        return tspData;
    }

    private static ArrayList<String> readData(String filename) {
        ArrayList lines = new ArrayList();
        Scanner s = null;
        File infile = new File(filename);

        try {
            s = new Scanner(infile);
        } catch (FileNotFoundException var5) {
            var5.printStackTrace();
        }

        while(s != null && s.hasNext()) {
            lines.add(s.nextLine());
        }

        return lines;
    }

    public static double[] quadratic(double a, double b, double c) {
        double disc = Math.sqrt(b * b - 4.0D * a * c);
        if(disc < 0.0D) {
            System.out.println("There are no real roots!");
            return null;
        } else {
            double[] roots = new double[]{(-b + disc) / (2.0D * a), 0.0D};
            System.out.println("Root: " + roots[0]);
            roots[1] = (-b - disc) / (2.0D * a);
            System.out.println("Root: " + roots[1]);
            return roots;
        }
    }
}
