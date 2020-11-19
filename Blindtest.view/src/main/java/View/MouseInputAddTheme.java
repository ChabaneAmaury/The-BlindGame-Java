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

    /**
     * Instantiates a new mouse input add theme.
     *
     * @param addThemePanel the add theme panel
     */
    public MouseInputAddTheme(AddThemePanel addThemePanel) {
        super(addThemePanel);

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

        /** The btn Y. */
        int btnY = (int) (this.panel.getHeight() - (this.panel.getWidth() / 51.2)
                - (this.panel.getHeight() / 10.2857143));
        /** The btn W. */
        int btnW = this.panel.getWidth() / 12;
        /** The btn H. */
        int btnH = this.panel.getHeight() / 14;
        /** The search X. */
        int searchX = (int) (this.panel.getWidth() / 1.74545455);
        /** The search Y. */
        int searchY = (int) (this.panel.getHeight() / 16.5);
        /** The back X. */
        int backX = (int) (this.panel.getWidth() - (this.panel.getWidth() / 51.2) - (this.panel.getWidth() / 10));

        if ((my >= searchY) && (my <= (searchY + btnH))) {
            if ((mx >= searchX) && (mx <= (searchX + btnW))) {
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
        } else if ((my >= btnY) && (my <= (btnY + btnH))) {
            if ((mx >= backX) && (mx <= (backX + btnW))) {
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
