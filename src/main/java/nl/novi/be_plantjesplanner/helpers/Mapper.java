package nl.novi.be_plantjesplanner.helpers;

import nl.novi.be_plantjesplanner.dtos.*;
import nl.novi.be_plantjesplanner.entities.*;

public class Mapper {

    //FROM-DTO mappers
    public static SelectedPlant mapFromSelectedPlantDto(SelectedPlantDto selectedPlantDto){
        SelectedPlant newSelectedPlant = new SelectedPlant();
        newSelectedPlant.setQuantity(selectedPlantDto.quantity());

//      newSelectedPlant.setDesign(mapFromDesignDto(selectedPlantDto.designDto());//todo design entity rechtbreien
        newSelectedPlant.setPlant(mapFromPlantDto(selectedPlantDto.plantDto()));
        return newSelectedPlant;
    }

    public static Plant mapFromPlantDto(PlantDto plantDto){
        Plant newPlant = new Plant();
        newPlant.setDutchName(plantDto.dutchName());
        newPlant.setLatinName(plantDto.latinName());
        newPlant.setDescription(plantDto.description());
        newPlant.setHeight(plantDto.height());
        newPlant.setFootprint(plantDto.footprint());
        newPlant.setBloomColorHex(plantDto.bloomColorHex().toUpperCase());
        newPlant.setBloomColorGroup(plantDto.bloomColorGroup());
        newPlant.setPublished(plantDto.published());
        if (plantDto.bloomingCalendarDto() != null) {
            newPlant.setBloomingCalendar(mapFromBloomingCalendarDto(plantDto.bloomingCalendarDto()));
        }

        if (plantDto.localeDto() != null) {
            newPlant.setLocale(mapFromLocaleDto(plantDto.localeDto()));
        }

        if (plantDto.plantAvatarDto() != null) {
            newPlant.setPlantAvatar(mapFromImageMetadataDto(plantDto.plantAvatarDto()));
        }

        return newPlant;
    }

    public static BloomingCalendar mapFromBloomingCalendarDto(BloomingCalendarDto bloomingCalendarDto){
        BloomingCalendar newBloomingCalendar = new BloomingCalendar();
        newBloomingCalendar.setJanuary(bloomingCalendarDto.january());
        newBloomingCalendar.setFebruary(bloomingCalendarDto.february());
        newBloomingCalendar.setMarch(bloomingCalendarDto.march());
        newBloomingCalendar.setApril(bloomingCalendarDto.april());
        newBloomingCalendar.setMay(bloomingCalendarDto.may());
        newBloomingCalendar.setJune(bloomingCalendarDto.june());
        newBloomingCalendar.setJuly(bloomingCalendarDto.july());
        newBloomingCalendar.setAugust(bloomingCalendarDto.august());
        newBloomingCalendar.setSeptember(bloomingCalendarDto.september());
        newBloomingCalendar.setOctober(bloomingCalendarDto.october());
        newBloomingCalendar.setNovember(bloomingCalendarDto.november());
        newBloomingCalendar.setDecember(bloomingCalendarDto.december());
        return newBloomingCalendar;
    }

    public static Locale mapFromLocaleDto(LocaleDto localeDto){
        Locale newLocale = new Locale();
        newLocale.setSunlight(localeDto.sunlight());
        newLocale.setMoisture(localeDto.moisture());
        newLocale.setWindTolerance(localeDto.windTolerance());
        newLocale.setSoilType(localeDto.soilType());
        newLocale.setOpenGroundOnly(localeDto.openGroundOnly());
        return newLocale;
    }

    public static Image mapFromImageMetadataDto(ImageMetadataDto plantAvatarDto){
        Image newAvatar = new Image();
        newAvatar.setOriginalFilename(plantAvatarDto.originalFilename());
        newAvatar.setStoredFilename(plantAvatarDto.storedFilename());
        return newAvatar;
    }

    public static Design2 mapFromDesign2Dto(Design2Dto design2Dto){
        Design2 newDesign2 = new Design2();
        newDesign2.setTitle(design2Dto.title());
        return newDesign2;
    }

    public static User mapFromUserDto(UserDto userDto){
    User newUser = new User();
    newUser.setUsername(userDto.username());
    newUser.setPassword(userDto.password());
//    newUser.setRole(userDto.role());

    return newUser;
    }

//    public static SelectedPlant mapFromSelectedPlantDto(SelectedPlantDto selectedPlantDto){
//        SelectedPlant newSelectedPlant = new SelectedPlant();
//        newSelectedPlant.setQuantity(selectedPlantDto.quantity());
//
////      newSelectedPlant.setDesign(mapFromDesignDto(selectedPlantDto.designDto());//todo design entity rechtbreien
//        newSelectedPlant.setPlant(mapFromPlantDto(selectedPlantDto.plantDto()));
//        return newSelectedPlant;
//    }


//TO DTO mappers
//    public static SelectedPlantDto mapToSelectedPlantDto(SelectedPlant selectedPlant){
//        //map child objects first
//        PlantDto newPlantDto = Mapper.mapToPlantDto(selectedPlant.getPlant());
//
//        //DesignDto newDesignDto = Mapper.mapToDesignDto(selectedPlant.getDesign());
//        SelectedPlantDto = new SelectedPlantDto(selectedPlant.getId(), selectedPlant.getQuantity(), )
//    }
    public static PlantDto mapToPlantDto(Plant plant){
        //map child objects first
        LocaleDto newLocaleDto = plant.getLocale() != null? Mapper.mapToLocaleDto(plant.getLocale()) : null;

        BloomingCalendarDto newBloomingCalendarDto = plant.getBloomingCalendar() != null? Mapper.mapToBloomingCalendarDto(plant.getBloomingCalendar()) : null;

        ImageMetadataDto newImageMetadataDto = plant.getPlantAvatar() != null? Mapper.mapToImageMetadataDto(plant.getPlantAvatar()) : null;

        PlantDto newPlantDto = new PlantDto(plant.getId(), plant.getDutchName(), plant.getLatinName(), plant.getDescription(), plant.getHeight(), plant.getFootprint(), plant.getBloomColorHex(), plant.getBloomColorGroup(), plant.isPublished(), newLocaleDto, newBloomingCalendarDto, newImageMetadataDto);
        return newPlantDto;
    }

    public static BloomingCalendarDto mapToBloomingCalendarDto(BloomingCalendar bloomingCalendar) {
        BloomingCalendarDto newBloomingCalendarDto = new BloomingCalendarDto(bloomingCalendar.bloomsInJanuary(), bloomingCalendar.bloomsInFebruary(), bloomingCalendar.bloomsInMarch(), bloomingCalendar.bloomsInApril(), bloomingCalendar.bloomsInMay(), bloomingCalendar.bloomsInJune(), bloomingCalendar.bloomsInJuly(), bloomingCalendar.bloomsInAugust(), bloomingCalendar.bloomsInSeptember(), bloomingCalendar.bloomsInOctober(), bloomingCalendar.bloomsInNovember(), bloomingCalendar.bloomsInDecember());
        return newBloomingCalendarDto;
    }
    public static LocaleDto mapToLocaleDto(Locale locale){
        LocaleDto newLocaleDto = new LocaleDto(locale.getSunlight(), locale.getMoisture(), locale.getWindTolerance(),locale.getSoilType(),locale.getOpenGroundOnly());
        return newLocaleDto;
    }

    public static ImageMetadataDto mapToImageMetadataDto(Image image){
        ImageMetadataDto newImageMetadataDto = new ImageMetadataDto(image.getId(), image.getOriginalFilename(), image.getStoredFilename(),image.getUploadDateTime());
        return newImageMetadataDto;
    }

    public static Design2Dto mapToDesign2Dto(Design2 design2){
        Design2Dto newDesign2Dto = new Design2Dto(design2.getId(), design2.getTitle());
        return newDesign2Dto;
    }

    public static UserDto mapToUserDto(User user){
        //map child object design first
        UserDto newUserDto =  new UserDto(user.getId(), user.getUsername(), "********", user.getRole(), user.getCreationDate());
        return newUserDto;
    }


}
