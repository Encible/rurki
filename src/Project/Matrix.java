package Project;

import static java.lang.Math.*;

public class Matrix {


    static int boundMin = -1;
    static int boundMax = 1;
    static int N = 2;
    static int dim = 9;


    static double k(double x, double y) {
        return 1;
    }

    static double g(double x, double y) {
        return cbrt(x * x);
    }

    static boolean isOutside(double x, double y, double xmin, double xmax, double ymin, double ymax) {
        return x < xmin || x > xmax || y < ymin || y > ymax;
    }

    static double phi(int i, double x, double y, double x0, double y0) {
        if (isOutside(x, y, x0, x0 + 1, y0 - 1, y0)) return 0;
        switch (i) {
            case 0:
                return -(x - 1 - x0) * (y + 1 - y0);
            case 1:
                return (x - x0) * (y + 1 - y0);
            case 2:
                return (x - 1 - x0) * (y - y0);
            case 3:
                return -(x - x0) * (y - y0);
            default:
                return 9999;
        }
    }

    static double phidx(int i, double x, double y, double x0, double y0) {
        if (isOutside(x, y, x0, x0 + 1, y0 - 1, y0)) return 0;
        switch (i) {
            case 0:
                return -(y + 1 - y0);
            case 1:
                return (y + 1 - y0);
            case 2:
                return (y - y0);
            case 3:
                return -(y - y0);
            default:
                return 9999;
        }
    }

    static double phidy(int i, double x, double y, double x0, double y0) {
        if (isOutside(x, y, x0, x0 + 1, y0 - 1, y0)) return 0;
        switch (i) {
            case 0:
                return -(x - 1 - x0);
            case 1:
                return (x - x0);
            case 2:
                return (x - 1 - x0);
            case 3:
                return -(x - x0);
            default:
                return 9999;
        }
    }


    static double get_x0(int eParam, int phiParam) {
        return boundMin + (eParam % 3) - ((phiParam + 1) % 2);
    }

    static double get_y0(int eParam, int phiParam) {
        return boundMax - (eParam / 3) + (((phiParam / 2) + 1) % 2);
    }


    static double getBComponent(double x0, double y0, int phiParam1, int phiParam2) {

        double middleX = x0 + 1.0 / 2.0;
        double middleY = y0 - 1.0 / 2.0;

        return k(middleX, middleY) * phidx(3 - phiParam1, middleX, middleY, x0, y0) * phidx(3 - phiParam2, middleX, middleY, x0, y0)
                + k(middleX, middleY) * phidy(3 - phiParam1, middleX, middleY, x0, y0) * phidy(3 - phiParam2, middleX, middleY, x0, y0);
    }


    static double getB(int i, int j) {
        if ((i % 3 >= boundMax && i / 3 <= boundMax) || (j % 3 >= boundMax && j / 3 <= boundMax)) return 0;

        double B = 0;
        if (i == j) {
            for (int n = 0; n < 4; n++) {
                double x0 = get_x0(i, n);
                double y0 = get_y0(i, n);

                if (x0 < boundMin || x0 >= boundMax || y0 <= boundMin || y0 > boundMax)
                    continue;
                B += getBComponent(x0, y0, n, n);
            }
        } else {
            int phiParam1[] = new int[2];
            int phiParam2[] = new int[2];
            int smol = min(i, j);
            int huge = max(i, j);

            if (smol + 3 == huge) {
                phiParam1[0] = 2;
                phiParam2[0] = 2;
                phiParam1[1] = 3;
                phiParam2[1] = 3;
            }
            if (smol + 1 == huge) {
                phiParam1[0] = 1;
                phiParam2[0] = 0;
                phiParam1[1] = 3;
                phiParam2[1] = 2;
            } else return 0;

            for (int n = 0; n < 2; n++) {
                double x0 = get_x0(smol, phiParam1[n]);
                double y0 = get_y0(smol, phiParam1[n]);
                if (x0 < boundMin || x0 >= boundMax || y0 <= boundMin || y0 > boundMax) continue;

                B += getBComponent(x0, y0, phiParam1[n], phiParam2[n]);
            }
        }
        return B;
    }

    static double getLComponent(int i, double x0, double y0, double middleX, double middleY) {
        i = 3 - i;
        return k(middleX, middleY) * g(middleX, middleY) * phi(i, middleX, middleY, x0, y0);
    }

    static double getL(int j) {
        if (j == 1 || j == 2 || j == 4 || j == 5) return 0;

        double L = 0;
        double x0;
        double y0;
        double D = 1;


         if (j == 0) {
            x0 = get_x0(j, 3);
            y0 = get_y0(j, 3);
            L += getLComponent(3, x0, y0, x0 + D / 2, y0);
            L += getLComponent(3, x0, y0, x0, y0 - D / 2);
        } else if (j ==3) {
            x0 = get_x0(j, 1);
            y0 = get_y0(j, 1);
            L += getLComponent(1, x0, y0, x0, y0 - D / 2);
            L += getLComponent(3, x0, y0 - D, x0, y0 - 3 * D / 2);
        } else if (j == 6){
            x0 = get_x0(j, 1);
            y0 = get_y0(j, 1);
            L += getLComponent(1, x0, y0, x0, y0 - D / 2);
            L += getLComponent(1, x0, y0, x0 + D / 2, y0 - D);
        } else if (j ==7){
            x0 = get_x0(j, 0);
            y0 = get_y0(j, 0);
            L += getLComponent(0, x0, y0, x0 + D / 2, y0 - D);
            L += getLComponent(1, x0 + D, y0, x0 + 3 * D / 2, y0 - D);
        } else if (j == 8){
            x0 = get_x0(j, 0);
            y0 = get_y0(j, 0);
            L += getLComponent(0, x0, y0, x0 + D / 2, y0 - D);
            L += getLComponent(0, x0, y0, x0 + D, y0 - D / 2);
        }
        return L;
    }


    static double[][] getMatrixB() {
        double MatrixB[][] = new double[9][9];


        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                MatrixB[i][j] = getB(i, j);
                //System.out.print(MatrixB[i][j] + " ");
            }
            //System.out.println(" ");
        }
        return MatrixB;
    }

    static double[] getMatrixL() {
        double MatrixL[] = new double[9];

        for (int i = 0; i < 9; i++) {
            MatrixL[i] = getL(i);
           // System.out.println(MatrixL[i]);
        }
        return MatrixL;
    }


    static double getTemp(double x, double y, double[] outcome) {
        double temp = 0;
        for (int i = 0; i < 9; i++) {
            double x0 = get_x0(i, 3);
            double y0 = get_y0(i, 3);

            double xArr[] = {x0, x0 - 1, x0, x0 - 1};
            double yArr[] = {y0, y0, y0 + 1, y0 + 1};

            for (int n = 0; n < 4; n++) {
                x0 = xArr[n];
                y0 = yArr[n];
                if (x0 < -1 || x0 >= 1 || y0 <= -1 || y0 > 1 || (x0 >= 0 && y0 > 0)) continue;

                temp += outcome[i] * phi(n, x, y, x0, y0);
            }

        }
        return temp;
    }

    static double[][] getFinalOutcome(int width, double[] W) {
        double quantum = 1 / (double) width;

        int size = (int) ((boundMax - boundMin) / quantum);

        double[][] outcome = new double[size][size];

        double y = boundMin + quantum / 2;
        for (int i = 0; i < size; i++) {
            double x = boundMin + quantum / 2;
            for (int j = 0; j < size; j++) {
                if (x > 0 && y > 0) outcome[i][j] = -1;
                else outcome[i][j] = getTemp(x, y, W);
                x += quantum;
            }
            y += quantum;
        }
        return outcome;
    }
}
