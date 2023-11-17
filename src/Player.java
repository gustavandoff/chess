

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author gustav.andoff
 */
public class Player {

    private String name;

    public Player(String name) throws NameException {
        if (name.length() == 0 || (name.charAt(0) < 65 || name.charAt(0) > 90 && name.charAt(0) < 97 || name.charAt(0) > 122)) {
            throw new NameException("Ditt namn måste börja med en bokstav");
        } else {
            this.name = name;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
