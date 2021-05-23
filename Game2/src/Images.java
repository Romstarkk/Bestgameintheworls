import javax.swing.*;
import java.awt.*;

public class Images {
    private String[] id=new String[14];
    private Object[] images;

    Images(){
        id[0]="Num_0";
        id[1]="Num_1";
        id[2]="Num_2";
        id[3]="Num_3";
        id[4]="Num_4";
        id[5]="Num_5";
        id[6]="Num_6";
        id[7]="Num_7";
        id[8]="Num_8";
        id[9]="Bomb";
        id[10]="Flag";
        id[11]="Num_close";
        id[12]="W";
        id[13]="L";
        initImage();
    }

    private void initImage(){
        images=new Object[14];
        for(int i=0;i<14;i++){
            images[i]=LoadImage(id[i]);
        }
    }

    private Image LoadImage(String id)
    {
        String filePath= id +".png";
        ImageIcon icon=new ImageIcon(getClass().getResource(filePath));
        return icon.getImage();
    }
    public Image getImage(int id){
        return (Image) images[id];
    }
}
