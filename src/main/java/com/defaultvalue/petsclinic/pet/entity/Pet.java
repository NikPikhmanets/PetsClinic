package com.defaultvalue.petsclinic.pet.entity;


import com.defaultvalue.petsclinic.pet.kind.KindOfPet;

import javax.persistence.*;

@Entity(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "pets_kinds",
            joinColumns = @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "kind_id"))
    private KindOfPet kindOfPet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public KindOfPet getKindOfPet() {
        return kindOfPet;
    }

    public void setKindOfPet(KindOfPet kindOfPet) {
        this.kindOfPet = kindOfPet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pet pet = (Pet) o;

        if (userId != pet.userId) return false;
        if (!id.equals(pet.id)) return false;
        if (!name.equals(pet.name)) return false;
        return kindOfPet.equals(pet.kindOfPet);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + kindOfPet.hashCode();
        return result;
    }
}
