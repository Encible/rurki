package Project;

import java.awt.*;

import static Project.GaussianElimination.lsolve;
import static Project.Matrix.*;

public class VisualRunner {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {


                double matrixL[] = getMatrixL();
                double matrixB[][] = getMatrixB();
                for(int i=0; i<9; i++) {
                    if(matrixB[i][i]==0) matrixB[i][i] = 1;
                }
                System.out.println("\n\n\n\n");
                double outcome[]  = lsolve(matrixB, matrixL);

                double wynikki[][] = getFinalOutcome(50, outcome);


                new MainFrame(wynikki);



            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

