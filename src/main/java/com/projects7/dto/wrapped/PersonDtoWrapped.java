package com.projects7.dto.wrapped;

import com.projects7.dto.PersonDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PersonDtoWrapped extends Wrapper<PersonDto> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3669287481333659034L;

	public PersonDtoWrapped(List <PersonDto> availableEntityDto) {
		if (availableEntityDto == null) {
			this.setSuccess(false);
			this.setMessage("Alert_GetOrCreateCardCacheAsync_error");
			this.setSnackbarType("error");
			this.setContent(null);
		} else {
			this.setSuccess(true);
			this.setMessage("Alert_GetOrCreateCardFromCacheAsync_info");
			this.setSnackbarType("info");
			this.setContent(availableEntityDto);
		}
	}

}
