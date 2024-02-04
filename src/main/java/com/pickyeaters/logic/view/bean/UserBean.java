package com.pickyeaters.logic.view.bean;

import com.pickyeaters.logic.controller.exception.BeanException;
import com.pickyeaters.logic.controller.exception.LoginControllerException;
import com.pickyeaters.logic.model.*;

public class UserBean {
    public enum UserType{
        PICKIE,
        RESTAURATEUR,
        ADMIN
    }
    private String ID;
    private String name;
    private String email;
    private UserType type;
    private RestaurantBean restaurant;

    public UserBean(String ID, String email, String name, UserType type) {
        setID(ID);
        setName(name);
        setEmail(email);
        setType(type);
    }

    public  UserBean(String ID, String email, String name, RestaurantBean restaurant) {
        setID(ID);
        setName(name);
        setEmail(email);
        setType(UserType.RESTAURATEUR);
    }
    public UserBean(User user) throws BeanException {
        this(
                user.getID(),
                user.getEmail(),
                user.getFirstname() + " " + user.getLastname(),
                typeFromUser(user)
        );
        if(type == UserType.RESTAURATEUR) {
            restaurant = new RestaurantBean(((Restaurateur) user).getRestaurant());
        }
    }
    public UserBean(String ID, String email, String name, String type) throws BeanException {
        this(
                ID,
                email,
                name,
                typeFromString(type)
        );
    }
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getID() {
        return ID;
    }

    public UserType getType() {
        return type;
    }

    public RestaurantBean getRestaurant() {
        return restaurant;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public void setRestaurant(RestaurantBean restaurant) {
        this.restaurant = restaurant;
    }

    private static UserType typeFromUser(User user) throws BeanException {
        if(user instanceof Pickie) {
            return UserType.PICKIE;
        } else if (user instanceof Restaurateur) {
            return UserType.RESTAURATEUR;
        } else if (user instanceof Administrator) {
            return UserType.ADMIN;
        } else {
            throw new BeanException("Cannot identify user type");
        }
    }

    private static UserType typeFromString(String user) throws BeanException {
        return switch (user) {
            case "PICKIE" -> UserType.PICKIE;
            case "REST" -> UserType.RESTAURATEUR;
            case "ADMIN" -> UserType.ADMIN;
            default -> throw new BeanException("Unexpected value: " + user);
        };
    }
}
