package Models;

public class IllnessClassification
{
    private String category;
    private String description;

    public IllnessClassification(String category)
    {
        setCategory(category);
    }

    public void setCategory(String category)
    {
        this.category = category;
        setDescription(getCategory());
    }

    public String getCategory()
    {
        return category;
    }

    public void setDescription(String category)
    {
        switch (category)
        {
            case "A":
                this.description = "Niektóre choroby zakaźne i pasożytnicze";
                break;
            case "B":
                this.description = "Niektóre choroby zakaźne i pasożytnicze";
                break;
            case "C":
                this.description = "Nowotwory";
                break;
            case "D":
                this.description = "Choroby krwi i narządów krwiotwórczych oraz niektóre choroby przebiegające z udziałem mechanizmów autoimmunologicznych";
                break;
            case "E":
                this.description = "Zaburzenia wydzielania wewnętrznego, stanu odżywienia i przemiany metabolicznej";
                break;
            case "F":
                this.description = "Zaburzenia psychiczne i zaburzenia zachowania";
                break;
            case "G":
                this.description = "Choroby układu nerwowego";
                break;
            case "H":
                this.description = "Choroby oka i przydatków oka, ucha i wyrostka sutkowatego";
                break;
            case "I":
                this.description = "Choroby układu krążenia";
                break;
            case "J":
                this.description = "Choroby układu oddechowego";
                break;
            case "K":
                this.description = "Choroby układu trawiennego";
                break;
            case "L":
                this.description = "Choroby skóry i tkanki podskórnej";
                break;
            case "M":
                this.description = "Choroby układu kostno-mięśniowego i tkanki łącznej";
                break;
            case "N":
                this.description = "Choroby układu moczowo-płciowego";
                break;
            case "O":
                this.description = "Ciąża, poród i połóg";
                break;
            case "P":
                this.description = "Niektóre stany rozpoczynające się w okresie okołoporodowym";
                break;
            case "Q":
                this.description = "Wady rozwojowe wrodzone, zniekształcenia i aberracje chromosomowe";
                break;
            case "R":
                this.description = "Objawy, cechy chorobowe oraz nieprawidłowe wyniki badań klinicznych gdzie indziej niesklasyfikowane";
                break;
            case "S":
                this.description = "Urazy, zatrucia i inne określone skutki działania czynników zewnętrznych";
                break;
            case "T":
                this.description = "Urazy, zatrucia i inne określone skutki działania czynników zewnętrznych";
                break;
            case "U":
                this.description = "Kody specjalne";
                break;
            case "V":
                this.description = "Zewnętrzne przyczyny zachowania i zgonu";
                break;
            case "W":
                this.description = "Zewnętrzne przyczyny zachowania i zgonu";
                break;
            case "X":
                this.description = "Zewnętrzne przyczyny zachowania i zgonu";
                break;
            case "Y":
                this.description = "Zewnętrzne przyczyny zachowania i zgonu";
                break;
            case "Z":
                this.description = "Czynniki wpływające na stan zdrowia i kontakt ze służbą zdrowia";
                break;
            default:
                break;

        }

    }

    public String getDescription()
    {
        return description;
    }

    @Override
    public String toString() {
        return getCategory();
    }
}
