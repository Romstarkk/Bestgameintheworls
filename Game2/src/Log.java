
import java.util.Random;

public class Log {
    private boolean[][] mat_vision;//матрица отображений true-отображать
    private boolean[][] mat_bomb;//матрица бомб true-есть бомба
    private int[][] mat_search;//матрица с цифрами int-кол-во бомб вокруг клетки
    private boolean[][] mat_flag;//матрица флагов true-есть флаг
    private int Cols;
    private int Rows;
    private boolean isDead;
    private boolean isGG;

    Log(int Cols,int Rows,int bombs){
        isDead=false;
        isGG=false;
        this.Cols=Cols;
        this.Rows=Rows;
        initBomb(Cols,Rows,bombs);
        initSearch(Cols,Rows);
        initVision(Cols,Rows);
        initFlag(Cols,Rows);
        isWin();
    }

    private void initFlag(int cols,int rows){
        mat_flag=new boolean[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                mat_flag[i][j]=false;
            }
        }
    }
    private void initVision(int cols, int rows) {
        mat_vision=new boolean[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                mat_vision[i][j]=false;
            }
        }
    }
    private void initSearch(int cols, int rows) {
        mat_search=new int[cols][rows];
        for(int i=0;i<cols;i++){
            for (int j=0;j<rows;j++) {
                int numberBomb = 0;
                numberBomb = searchCell(i - 1, j - 1) +
                             searchCell(i, j - 1) +
                             searchCell(i + 1, j - 1) +
                             searchCell(i - 1, j) +
                             searchCell(i + 1, j) +
                             searchCell(i - 1, j + 1) +
                             searchCell(i, j + 1) +
                             searchCell(i + 1, j + 1);
                mat_search[i][j]=numberBomb;
            }
        }
    }
    private void initBomb(int cols,int rows,int bombs){
        mat_bomb=new boolean[cols][rows];
        Random random=new Random();

        if(bombs<=cols*rows){
            int i=0;

            while (i<bombs){
                int ox=random.nextInt(cols);
                int oy=random.nextInt(rows);

                if(mat_bomb[ox][oy]==false){
                    i++;
                    mat_bomb[ox][oy]=true;
                    System.out.println("Bomb in x= "+ox+" y= "+oy);
                }
            }
        }
    }
    private int searchCell(int x,int y) {
        if (x >= 0 && x < Cols) {
            if (y >= 0 && y < Rows) {
                if (mat_bomb[x][y] == true) {
                    return 1;
                }
            }
        }
        return 0;
    }

    public void click(int ox,int oy,int id)//id 1-ЛКМ 2-ПКМ
    {

        if(isDead||isGG)return;
        if(id==3){
            mat_flag[ox][oy]=!mat_flag[ox][oy];
        }
        if(id==1){
            if(mat_bomb[ox][oy]==true) {
                System.out.println("DEAD");
                dead();
            }

            if(mat_flag[ox][oy]==true){
                mat_flag[ox][oy]=false;
            }
            if(mat_vision[ox][oy]==false){
                mat_vision[ox][oy]=true;
                openAdjacentCell(ox,oy);
            }
        }
        isWin();
    }

    private void openAdjacentCell(int ox,int oy) {
        if (mat_search[ox][oy] == 0) {
            for (int i = ox - 1; i < ox + 2; i++) {
                for (int j = oy - 1; j < oy + 2; j++) {
                    if(isAvailable(i,j)){
                        if(mat_vision[i][j]==false){
                            mat_vision[i][j]=true;
                            System.out.println("Cell["+i+" , "+j+"] - is open");
                            openAdjacentCell(i,j);
                        }
                    }
                }
            }
        }
    }

    private boolean isAvailable(int ox,int oy){
        if(ox>=0&&ox<Cols&&oy>=0&&oy<Rows)return true;
        return false;
    }

    private void dead() {
        isDead=true;
    }

    public int getID(int x,int y){
        if(isDead==true){
            if(mat_bomb[x][y])return 9;
        }
        if(mat_flag[x][y]==true)return 10;

        if(mat_vision[x][y]==true){
            return mat_search[x][y];
        }else {
            return 11;
        }
    }

    private void isWin(){
        boolean ret=true;
        for(int i=0;i<Cols;i++){
            for(int j=0; j<Rows;j++){
               if(mat_bomb[i][j]==false&&mat_vision[i][j]==false)ret=false;
            }
        }
        if(ret)isGG=true;
    }

    public int getStat()//возвращает состояние игры (-1)-поражение 0-игра идет 1-победа
    {
        if(isDead)return -1;
        if(isGG)return 1;
        return 0;
    }
}
