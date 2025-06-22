package nl.novi.be_plantjesplanner.dtos;

import nl.novi.be_plantjesplanner.enumerations.Role;

import java.time.ZonedDateTime;

public record UserDto(Long id, String username, String password, Role role, ZonedDateTime creationDate, Design2Dto design2Dto) {
}
