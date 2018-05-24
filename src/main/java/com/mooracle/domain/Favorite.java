package com.mooracle.domain;

import javax.persistence.*;

/** ENTRY 7: Creating Favorite @Entity class
 *  1.  This class is to define Favorite entity
 *  2.  One Place can have many User entities to chose as favorite in order to be placed in the User's interface
 *  3.  Those users will be placed in Join column named as "user_id"
 *  4.  This entity class will have class inside this class to make FavoriteBuilder
 *  5.  User object will not be set or get in here it just as reference since it will be many user in one favorite
 *  6.  The inner class is used as builder for the Favorite Object since it will require a long data to initialize
 *
 * */

@Entity
public class Favorite {
    //field id declaration:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //non column field declaration:
    private String formattedAddress;
    private String placeId;

    //getters and setters:

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    //build class FavoriteBuilder inside this class:
    public static class FavoriteBuilder {
        //field declaration for building a Favorite:
        private Long id;
        private String formattedAddress;
        private String placeId;
        //User is not included here since user will build it and reference into it.

        //building constructor using id as FavoriteBuilder:
        public FavoriteBuilder(Long id){
            this.id = id;
        }

        //building default constructor:
        public FavoriteBuilder() {}

        //building Favorite with id
        public FavoriteBuilder withId(Long id){
            this.id = id;
            return this;
        }

        //building Favorite with formattedAddress
        public FavoriteBuilder withAddress(String formattedAddress){
            this.formattedAddress = formattedAddress;
            return this;
        }

        //building Favorite with PlaceId
        public FavoriteBuilder withPlaceId(String placeId){
            this.placeId = placeId;
            return this;
        }

        //recap all builders above to form new favorite
        public Favorite build() {
            Favorite favorite = new Favorite();
            favorite.setId(id);
            favorite.setFormattedAddress(formattedAddress);
            favorite.setPlaceId(placeId);
            return favorite;
        }
    }
}
