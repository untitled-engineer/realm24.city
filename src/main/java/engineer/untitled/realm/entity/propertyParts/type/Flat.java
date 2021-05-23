package engineer.untitled.realm.entity.propertyParts.type;

import engineer.untitled.realm.entity.propertyParts.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity(name = "Flat")
@Table(name = "properties_building")
public class Flat extends Type {


    @NotBlank
    private String typeName;

    // -- About property -- Об объекте

    // тип жилья
    private int housingType;

    // Количество комнат
    private int numberOfRooms;

    // Этаж
    private int floor;

    private int buildingTotalFloors;

    // -Планировка

    // Общая площадь
    private int areaTotal;

    // Жилая площадь
    private int araaLiving;

    // Кухня
    private int areaKitchen;

    // Лоджия
    private int loggia;

    // Балкон
    private int balcony;

    // Окна выходят
    private int windowsOverlook;

    // Раздельные санузлы
    private int separateBathrooms;

    // Совмещенные санузлы
    private int combinedBathrooms;

    // Ремонт
    private int repair;

    // Телефон
    private boolean telephone;

    // -- About the building -- О здании

    // Название
    private String title;

    // Год постройки
    private Date since;

    // Тип и серия дома
    private String buildingType;

    // Тип и серия дома
    private String buildingSeries;

    // Высота потолков
    private int ceilingHeight;

    // Пассажирских лифтов
    private int passengerElevators;

    // Грузовых лифтов
    private int freightElevators;

    // Пандус
    private boolean ramp;

    // Парковка
    private String parking;

    // -Фотографии

    public Flat(){

    }

    public Flat(boolean telephone, List<String> photos){
        this.telephone = telephone;
        this.typeName = this.getClass().getName();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

