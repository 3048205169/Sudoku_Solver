import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class testing {
    public static void main(String[] args){
        long startTime=System.currentTimeMillis();   //获取开始时间
        test02();
        long endTime=System.currentTimeMillis(); //获取结束时间

        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");

    }
    public static void test02(){
        machineSolver ms = new machineSolver();
        ms.init();
        ms.solveSudoku(ms.board);
        ms.printBoard();
    }
    public static void test01(){
        humanSolver hs = new humanSolver();
        hs.init();
        hs.solve(1,1);  //将数字1从区域1开始填起
    }

}
class humanSolver{
    int [][]quyu={{0,0,0,0},
        {0,1,2,3},
        {0,4,5,6},
        {0,7,8,9}};

    int a[][] =  new int[10][10];
    int hang[][] = new int[10][10];
    int lie[][] = new int[10][10];
    int grid[][] = new int[10][10];
    int sum;

    int p[][]={{0,0},{1,1},{1,4},{1,7},{4,1},{4,4},{4,7},{7,1},{7,4},{7,7}};
    //p[i][0]和p[i][1]数组记录第i个区域的左上角的横纵坐标
    void init1() {
        int i,j,k;
        //memset(hang,0,sizeof(hang));
        //memset(lie,0,sizeof(lie));
        //memset(grid,0,sizeof(grid));
        sum=0;
        for(i=1;i<=9;i++){
            for(j=1;j<=9;j++) {
                Scanner s = new Scanner(System.in);
                k = s.nextByte();
                a[i][j]=k;
                if(k!=0){
                    hang[i][k]=1;    //第i行填入k了
                    lie[j][k]=1;       //第j列填入k了
                    grid[quyu[(i-1)/3+1][(j-1)/3+1]][k]=1;  //  (i , j)所在区域填入k了
                    sum++;
                }
            }
        }
    }
    void init() {
        int i,j,k;
        //memset(hang,0,sizeof(hang));
        //memset(lie,0,sizeof(lie));
        //memset(grid,0,sizeof(grid));
        sum=0;
        initA();
        for(i=1;i<=9;i++){
            for(j=1;j<=9;j++) {
                k = a[i][j];
                if(k!=0){
                    hang[i][k]=1;    //第i行填入k了
                    lie[j][k]=1;       //第j列填入k了
                    grid[quyu[(i-1)/3+1][(j-1)/3+1]][k]=1;  //  (i , j)所在区域填入k了
                    sum++;
                }
            }
        }
    }
    public void initA(){
        a = new int[][]{
                {0,0,0,0,0,0,0,0,0,0},
                {0,7,0,0,0,0,0,0,0,2},
                {0,0,0,8,0,7,0,9,0,0},
                {0,1,0,0,0,0,0,0,5,0},
                {0,9,0,6,0,0,1,0,0,4},
                {0,5,0,0,6,8,0,0,0,0},
                {0,0,0,0,0,0,0,2,0,1},
                {0,0,7,0,0,0,8,3,2,0},
                {0,0,0,0,0,6,0,0,0,0},
                {0,0,0,3,9,0,0,0,0,6}
        };
    }


    void printBoard(){
        int i,j;
        for(i=1;i<=9;i++){
            for(j=1;j<=8;j++) {
                System.out.print(a[i][j]);
            }
            System.out.println(a[i][9]);
        }
    }

    void solve(int k1,int k2){  //将数字k1填入区域k2
        int x,y,i,j;
        if(sum==81) {
            printBoard();return;}  //数独全填好了就输出
        if(grid[k2][k1]!=0){   //区域k2中已有数字k1了
            if(k2<9) solve(k1,k2+1); //填下一个区域
            else solve(k1+1,1); //填下一个数字
        }
        x=p[k2][0];
        y=p[k2][1];
        for(i=x;i<=x+2;i++)
            for(j=y;j<=y+2;j++)
                if(a[i][j]==0 && hang[i][k1]==0 && lie[j][k1]==0) //第(i,j)格为空，且可填入k1
                {
                    a[i][j]=k1;
                    sum++;
                    hang[i][k1] = lie[j][k1] = 1;

                    if(k2<9) solve(k1,k2+1); //填下一个区域
                    else solve(k1+1,1); //填下一个数字
                    a[i][j]=0;
                    sum--;
                    hang[i][k1] = lie[j][k1] = 0;
                }

    }

}


class machineSolver {
    private boolean[][] line = new boolean[9][9];
    private boolean[][] column = new boolean[9][9];
    private boolean[][][] block = new boolean[3][3][9];
    private boolean valid = false;
    private List<int[]> spaces = new ArrayList<int[]>();

    public int[][] board;

    public void solveSudoku(int[][] board) {
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                if (board[i][j] == 0) {
                    spaces.add(new int[]{i, j});
                } else {
                    int digit = board[i][j] - 1;
                    line[i][digit] = column[j][digit] = block[i / 3][j / 3][digit] = true;
                }
            }
        }

        dfs(board, 0);
    }
    void printBoard(){
        int i,j;
        for(i=0;i<9;i++){
            for(j=0;j<8;j++) {
                System.out.print(board[i][j]);
            }
            System.out.println(board[i][8]);
        }
    }
    public void init(){
        board = new int[][]{
                {7,0,0,0,0,0,0,0,2},
                {0,0,8,0,7,0,9,0,0},
                {1,0,0,0,0,0,0,5,0},
                {9,0,6,0,0,1,0,0,4},
                {5,0,0,6,8,0,0,0,0},
                {0,0,0,0,0,0,2,0,1},
                {0,7,0,0,0,8,3,2,0},
                {0,0,0,0,6,0,0,0,0},
                {0,0,3,9,0,0,0,0,6}
        };
    }

    public void dfs(int[][] board, int pos) {
        if (pos == spaces.size()) {
            valid = true;
            return;
        }
        int[] space = spaces.get(pos);
        int i = space[0], j = space[1];
        for (int digit = 0; digit < 9 && !valid; ++digit) {
            if (!line[i][digit] && !column[j][digit] && !block[i / 3][j / 3][digit]) {
                line[i][digit] = column[j][digit] = block[i / 3][j / 3][digit] = true;
                board[i][j] = (digit  + 1);
                dfs(board, pos + 1);
                line[i][digit] = column[j][digit] = block[i / 3][j / 3][digit] = false;
            }
        }
    }
}
