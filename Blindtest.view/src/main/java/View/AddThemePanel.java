/*
 * @author Amaury Chabane
 */
package View;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddThemePanel extends MyPanel {

    private JTextField searchField = null;

    private String[] addStrings = { "Movie", "TV Show" };

    private Map<String, String> titlesMap = new HashMap<>();

    // Create the combo box, select item at index 4.
    // Indices start at 0, so 4 specifies the pig.
    private JComboBox<String> addList = new JComboBox<>(this.addStrings);

    private JSONArray resultArr = null;

    private Map<String, Image> searchResultImages = new HashMap<>();

    private String type = "Movie";

    /**
     *
     */
    private static final long serialVersionUID = 4443384506923642743L;

    public AddThemePanel(ViewFrame viewFrame) {
        super(viewFrame);

        this.getTitlesMap().put("Movie", "original_title");
        this.getTitlesMap().put("TV Show", "original_name");

        // TODO Auto-generated constructor stub
        MouseInputAddTheme mouseInput = new MouseInputAddTheme(this);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);

        this.setLayout(null);
        this.setSearchField(this.createTextField("", (int) (this.getViewFrame().getWidth() / 51.2),
                (int) (this.getViewFrame().getHeight() / 14.4), (int) (this.getViewFrame().getWidth() / 1.92) - 100,
                this.getViewFrame().getHeight() / 18));

        this.setTypeSearch();
    }

    private void setTypeSearch() {
        this.addList.setSelectedIndex(0);
        this.addList.setBounds(new Rectangle(
                ((int) (this.getViewFrame().getWidth() / 51.2) + (int) (this.getViewFrame().getWidth() / 1.92)) - 100,
                (int) (this.getViewFrame().getHeight() / 14.4), 100, this.getViewFrame().getHeight() / 18));
        this.add(this.addList);
    }

    public void drawTheme(Graphics2D graphics, JSONObject theme, double x, double y) {
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1)); // remove transparency
        Font basicFont = new Font(this.getFontName(), Font.BOLD,
                (int) ((this.getHeight() - this.getSearchField().getY() - this.getSearchField().getHeight()) / 28.8));
        graphics.setColor(Color.BLACK);
        graphics.setFont(basicFont);
        Font miniFont = new Font(this.getFontName(), Font.BOLD,
                (this.getHeight() - this.getSearchField().getY() - this.getSearchField().getHeight()) / 90);
        int rectX = (int) x;
        int rectY = (int) y;
        int rectW = this.getWidth() / 11;
        int rectH = (rectW * 160) / 120;
        int titleX = (int) (rectX + rectW + (this.getWidth() / 12.8));
        int titleY = rectY + (rectH / 2) + (graphics.getFont().getSize() / 2);

        String title = null;

        title = theme.getString(this.getTitlesMap().get(this.type));

        graphics.drawString(title, titleX, titleY);

        String poster_url = null;
        try {
            poster_url = theme.getString("poster_path");
        } catch (JSONException e) {
        }
        Image poster = null;

        if (poster_url != null) {
            if (!this.getSearchResultImages().containsKey(poster_url)) {

                poster = this.getViewFrame().getController().loadTMDbImage(poster_url).getScaledInstance(rectW, rectH,
                        Image.SCALE_SMOOTH);

                if ((poster_url != null) && (poster != null)) {
                    this.getSearchResultImages().put(poster_url, poster);
                }
            }

            poster = this.getSearchResultImages().get(poster_url);
        } else {
            if (!this.getSearchResultImages().containsKey(title)) {
                this.getSearchResultImages().put(title, null);
            }

            poster = null;
        }

        graphics.setColor(Color.BLACK);
        Point mousePos = this.getMousePosition();
        if (mousePos != null) {
            if ((mousePos.getX() >= rectX) && (mousePos.getX() <= (rectX + rectW))) {
                if ((mousePos.getY() >= rectY) && (mousePos.getY() <= (rectY + rectH))) {
                    float alpha = (float) 0.5; // draw half transparent
                    AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                    graphics.setComposite(ac);
                }
            }
        }

        if (poster != null) {
            this.drawImageRoundedCorners(graphics, poster, 15, rectX, rectY, rectW, rectH);
        } else {
            graphics.setBackground(Color.GRAY);
            graphics.clearRect(rectX, rectY, rectW, rectH);
            graphics.setColor(Color.BLACK);
            graphics.setFont(miniFont);
            this.drawCenteredString(graphics, "Image Not Found", rectX, rectY, rectW, rectH);
        }
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1)); // remove transparency
    }

    /**
     * Paint component.
     *
     * @param g
     *              the g
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        int btnY = (int) (this.getHeight() - (this.getWidth() / 51.2) - (this.getHeight() / 10.2857143));
        int btnW = this.getWidth() / 12;
        int btnH = this.getHeight() / 14;
        int searchX = (int) (this.getWidth() / 1.74545455);
        int searchY = (int) (this.getViewFrame().getHeight() / 16.5);
        int backX = (int) (this.getWidth() - (this.getWidth() / 51.2) - (this.getWidth() / 10));

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        this.drawGradientPaint(graphics);

        this.drawButton(graphics, "Search", searchX, searchY, btnW, btnH);
        this.drawButton(graphics, "Back", backX, btnY, btnW, btnH);

        if (this.getResultArr() != null) {
            for (int i = 0; i < 3; i++) {
                if (i < this.getResultArr().length()) {
                    this.drawTheme(graphics, this.getResultArr().getJSONObject(i), this.getWidth() / 51.2, this
                            .getSearchField().getY() + this.getSearchField().getHeight()
                            + (((((i + 1) * this.getWidth()) / 51.2) + ((((this.getWidth() / 11) * 160) / 120) * i))));
                }
            }
            for (int i = 3; i < 6; i++) {
                if (i < this.getResultArr().length()) {
                    this.drawTheme(graphics, this.getResultArr().getJSONObject(i),
                            (this.getWidth() / 51.2) + (this.getWidth() / 2),
                            this.getSearchField().getY() + this.getSearchField().getHeight()
                                    + (((((i - 3) + 1) * this.getWidth()) / 51.2))
                                    + ((((this.getWidth() / 11) * 160) / 120) * (i - 3)));
                }
            }
        }

        try {
            Thread.sleep((long) 16.6666667);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.repaint();
    }

    public JTextField getSearchField() {
        return this.searchField;
    }

    public void setSearchField(JTextField searchField) {
        this.searchField = searchField;
    }

    public JComboBox<String> getAddList() {
        return this.addList;
    }

    public JSONArray getResultArr() {
        return this.resultArr;
    }

    public void setResultArr(JSONArray resultArr) {
        this.resultArr = resultArr;
        this.searchResultImages.clear();
    }

    public Map<String, Image> getSearchResultImages() {
        return this.searchResultImages;
    }

    public void setSearchResultImages(Map<String, Image> searchResultImages) {
        this.searchResultImages = searchResultImages;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getTitlesMap() {
        return this.titlesMap;
    }

}
