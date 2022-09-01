package model;

public class Day {
    private int Icon;

    private String IconPhrase;

    private boolean HasPrecipitation;

    private String PrecipitationType;

    private String PrecipitationIntensity;

    public void setIcon(int Icon){
        this.Icon = Icon;
    }
    public int getIcon(){
        return this.Icon;
    }
    public void setIconPhrase(String IconPhrase){
        this.IconPhrase = IconPhrase;
    }
    public String getIconPhrase(){
        return this.IconPhrase;
    }
    public void setHasPrecipitation(boolean HasPrecipitation){
        this.HasPrecipitation = HasPrecipitation;
    }
    public boolean getHasPrecipitation(){
        return this.HasPrecipitation;
    }
    public void setPrecipitationType(String PrecipitationType){
        this.PrecipitationType = PrecipitationType;
    }
    public String getPrecipitationType(){
        return this.PrecipitationType;
    }
    public void setPrecipitationIntensity(String PrecipitationIntensity){
        this.PrecipitationIntensity = PrecipitationIntensity;
    }
    public String getPrecipitationIntensity(){
        return this.PrecipitationIntensity;
    }
}
