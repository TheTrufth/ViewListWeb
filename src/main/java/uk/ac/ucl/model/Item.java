package uk.ac.ucl.model;

public class Item {
    private String text;
    private String url;
    private String image;

    public Item(){
        this.text = "item";
        this.url = "/list.html";
        this.image = "https://semantic-ui.com/images/wireframe/image.png";
    }

    public Item(String text, String url, String image){
        this.text = text;
        this.url = url;
        this.image = image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        if (text != null){
            sb.append(text);
        }

        if (url != null && !url.equals("/list.html")){
            if (text != null){
                sb.append(" ; ");
            }
            sb.append(url);
        }
        if (image != null && !image.equals("https://semantic-ui.com/images/wireframe/image.png")){
            if (text != null){
                sb.append(" ; ");
            }
            sb.append(image);
        }
        return sb.toString();
    }
}
