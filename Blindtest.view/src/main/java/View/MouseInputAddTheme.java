/*
 * @author Amaury Chabane
 */
package View;

import java.awt.event.MouseEvent;

import org.json.JSONArray;

/**
 * The Class MouseInputAddTheme.
 *
 * @author Amaury Chabane
 */
public class MouseInputAddTheme extends MouseInput {

    /** The btn Y. */
    private int btnY;
    
    /** The btn W. */
    private int btnW;
    
    /** The btn H. */
    private int btnH;
    
    /** The search X. */
    private int searchX;
    
    /** The search Y. */
    private int searchY;
    
    /** The back X. */
    private int backX;

    /**
     * Instantiates a new mouse input add theme.
     *
     * @param addThemePanel the add theme panel
     */
    public MouseInputAddTheme(AddThemePanel addThemePanel) {
        super(addThemePanel);
        this.btnY = (int) (this.panel.getHeight() - (this.panel.getWidth() / 51.2)
                - (this.panel.getHeight() / 10.2857143));
        this.btnW = this.panel.getWidth() / 12;
        this.btnH = this.panel.getHeight() / 14;
        this.searchX = (int) (this.panel.getWidth() / 1.74545455);
        this.searchY = (int) (this.panel.getHeight() / 16.5);
        this.backX = (int) (this.panel.getWidth() - (this.panel.getWidth() / 51.2) - (this.panel.getWidth() / 10));
    }

    /**
     * Mouse pressed.
     *
     * @param e the e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if ((my >= this.searchY) && (my <= (this.searchY + this.btnH))) {
            if ((mx >= this.searchX) && (mx <= (this.searchX + this.btnW))) {
                JSONArray result = null;
                if (((AddThemePanel) this.panel).getAddList().getSelectedItem() == "Movie") {
                    result = this.viewframe.getController().requestTMDbMovie("movie",
                            ((AddThemePanel) this.panel).getSearchField().getText());
                } else if (((AddThemePanel) this.panel).getAddList().getSelectedItem() == "TV Show") {
                    result = this.viewframe.getController().requestTMDbMovie("tv",
                            ((AddThemePanel) this.panel).getSearchField().getText());
                }
                ((AddThemePanel) this.panel).setResultArr(result);
                ((AddThemePanel) this.panel)
                        .setType((String) ((AddThemePanel) this.panel).getAddList().getSelectedItem());
            }
        } else if ((my >= this.btnY) && (my <= (this.btnY + this.btnH))) {
            if ((mx >= this.backX) && (mx <= (this.backX + this.btnW))) {
                this.viewframe.setContentPane(new OptionsPanel(this.viewframe));
                this.viewframe.revalidate();
            }
        }

        else {
            if (((AddThemePanel) this.panel).getResultArr() != null) {
                for (int i = 0; i < 3; i++) {
                    if (i < ((AddThemePanel) this.panel).getResultArr().length()) {

                        int tY = (int) (((AddThemePanel) this.panel).getSearchField().getY()
                                + ((AddThemePanel) this.panel).getSearchField().getHeight()
                                + (((((i + 1) * this.panel.getWidth()) / 51.2)
                                        + ((((this.panel.getWidth() / 11) * 160) / 120) * i))));
                        int tX = (int) (this.panel.getWidth() / 51.2);
                        int tW = this.panel.getWidth() / 11;
                        int tH = (tW * 160) / 120;

                        if ((my >= tY) && (my <= (tY + tH))) {
                            if ((mx >= tX) && (mx <= (tX + tW))) {
                                this.viewframe.getController().createThemeFromSearch(
                                        ((AddThemePanel) this.panel).getResultArr().getJSONObject(i),
                                        ((AddThemePanel) this.panel).getType(),
                                        ((AddThemePanel) this.panel).getTitlesMap());
                                this.viewframe.getModel().fillThemesList();
                                this.viewframe.getModel().loadTypes();
                                this.viewframe.setContentPane(new MenuPanel(this.viewframe, 0));
                                this.viewframe.revalidate();
                            }
                        }

                    }
                }
                for (int i = 3; i < 6; i++) {
                    if (i < ((AddThemePanel) this.panel).getResultArr().length()) {

                        int tY = (int) (((AddThemePanel) this.panel).getSearchField().getY()
                                + ((AddThemePanel) this.panel).getSearchField().getHeight()
                                + ((((i - 3) + 1) * this.panel.getWidth()) / 51.2)
                                + ((((this.panel.getWidth() / 11) * 160) / 120) * (i - 3)));
                        int tX = (int) (this.panel.getWidth() / 51.2) + (this.panel.getWidth() / 2);
                        int tW = this.panel.getWidth() / 11;
                        int tH = (tW * 160) / 120;

                        if ((my >= tY) && (my <= (tY + tH))) {
                            if ((mx >= tX) && (mx <= (tX + tW))) {
                                this.viewframe.getController().createThemeFromSearch(
                                        ((AddThemePanel) this.panel).getResultArr().getJSONObject(i),
                                        ((AddThemePanel) this.panel).getType(),
                                        ((AddThemePanel) this.panel).getTitlesMap());
                                this.viewframe.getModel().fillThemesList();
                                this.viewframe.getModel().loadTypes();
                                this.viewframe.setContentPane(new MenuPanel(this.viewframe, 0));
                                this.viewframe.revalidate();
                            }
                        }
                    }
                }
            }
        }
    }
}
