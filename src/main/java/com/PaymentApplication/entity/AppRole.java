package com.PaymentApplication.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * Role entity for Payment application
 */
/**
@Entity
@Table(name = "role")
public class AppRole
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<AppUser> users;

    public AppRole()
    {
    }

    public AppRole(Long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Set<AppUser> getUsers()
    {
        return users;
    }

    public void setUsers(Set<AppUser> users)
    {
        this.users = users;
    }

    public void addUser(AppUser user)
    {
        if (this.users == null)
            this.users = new HashSet<>();

        this.users.add(user);
    }

    @Override
    public int hashCode()
    {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if ( !(o instanceof AppRole))
            return false;

        AppRole role = (AppRole) o;

        if (!id.equals(role.id))
            return false;

        return name.equals(role.name);
    }


    @Override
    public String toString()
    {
        return "AppRole [" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ']';
    }
}
 */
