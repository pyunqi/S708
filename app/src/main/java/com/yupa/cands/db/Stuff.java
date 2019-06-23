package com.yupa.cands.db;

public class Stuff {

    private int _id;
    private String _name;
    private String _picture;
    private int _quantity;
    private String _description;
    private Double _laitude;
    private Double _longitude;
    private  String _tag;

    public Stuff(){

    }

    public Stuff(int _id,String _name, String _picture, int _quantity, String _description, Double _laitude,Double _longitude, String _tag) {
        this.set_name(_name);
        this.set_picture(_picture);
        this.set_quantity(_quantity);
        this.set_description(_description);
        this.set_laitude(_laitude);
        this.set_longitude(_longitude);
        this.set_tag(_tag);
        this.set_id(_id);
    }


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_picture() {
        return _picture;
    }

    public void set_picture(String _picture) {
        this._picture = _picture;
    }

    public int get_quantity() {
        return _quantity;
    }

    public void set_quantity(int _quantity) {
        this._quantity = _quantity;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public Double get_laitude() {
        return _laitude;
    }

    public void set_laitude(Double _laitude) {
        this._laitude = _laitude;
    }

    public Double get_longitude() {
        return _longitude;
    }

    public void set_longitude(Double _longitude) {
        this._longitude = _longitude;
    }

    public String get_tag() {
        return _tag;
    }

    public void set_tag(String _tag) {
        this._tag = _tag;
    }
}
