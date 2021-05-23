import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Game extends JFrame {

    final static int COLS=25;//кол-во столбцев
    final static int ROWS=12;//кол-во строк
    final static int IMAGE_SIZE=50;//размер картинки
    final static int BOMBS=25;//кол-во бомб
    private JPanel panel;
    private Log log;
    private Images images;

    public static void main(String[] arg){
        new Game();
    }

    private Game(){
        log=new Log(COLS,ROWS,BOMBS);
        images=new Images();
        initPanel();
        initFrame();
        initMouse();
    }

    private void initPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int state = log.getStat();
                if (state != 0) {
                    if(state<0) {
                        g.drawImage(images.getImage(13), 0, 0, this);
                    }else {
                        g.drawImage(images.getImage(12), 0, 0, this);
                    }

                }
                if (state == 0) {
                    for (int i = 0; i < COLS; i++) {
                        for (int j = 0; j < ROWS; j++) {
                            g.drawImage(images.getImage(log.getID(i, j)), i * IMAGE_SIZE, j * IMAGE_SIZE, this);
                        }
                    }
                    System.out.println("PaintComponent");
                }

            }
        };

        setPreferredSize(new Dimension(IMAGE_SIZE * COLS+17, IMAGE_SIZE * ROWS+39));

        add(panel);
    }

    private void initFrame(){
        pack(); //устанавливает минимальный размер окна,достаточный для лтображения всех компонентов
        setDefaultCloseOperation(EXIT_ON_CLOSE); //закрывает программу при выходе из программы
        setTitle("«Сапер» вызывает привыкание!"); //заголовок окна
        setLocationRelativeTo(null); //ставит окно по центру экрана
        setResizable(false); //запрещает изменять размер окна
        setVisible(true); //делает окно видимым
    }

    private void initMouse (){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                super.mouseClicked(e);
                int ox=(int)((e.getX()-8)/IMAGE_SIZE);
                int oy=(int)((e.getY()-31)/IMAGE_SIZE);
                int id=e.getButton();
                System.out.println("MouseClicked ID-"+id+" x= "+ox+" y= "+oy);
                log.click(ox,oy,id);

                repaint();
            }
        });
        add(panel);
    }

}
