package nl.novi.be_plantjesplanner.helpers;

import nl.novi.be_plantjesplanner.dtos.BloomingCalendarDto;
import nl.novi.be_plantjesplanner.dtos.ImageMetadataDto;
import nl.novi.be_plantjesplanner.dtos.LocaleDto;
import nl.novi.be_plantjesplanner.dtos.PlantDto;
import nl.novi.be_plantjesplanner.entities.BloomingCalendar;
import nl.novi.be_plantjesplanner.entities.Image;
import nl.novi.be_plantjesplanner.entities.Locale;
import nl.novi.be_plantjesplanner.entities.Plant;

public class Mapper {

    //FROM-DTO mappers
    public static Plant mapFromPlantDto(PlantDto plantDto){
        Plant newPlant = new Plant();
        newPlant.setDutchName(plantDto.dutchName());
        newPlant.setLatinName(plantDto.latinName());
        newPlant.setDescription(plantDto.description());
        newPlant.setHeight(plantDto.height());
        newPlant.setFootprint(plantDto.footprint());
        newPlant.setBloomColorHex(plantDto.bloomColorHex());
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
//TO DTO mappers

    public static PlantDto mapToPlantDto(Plant plant){
        //map child entities first for clarity
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

}
