package utils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

public class ObjectMapperUtils {
	
	private static ModelMapper modelMapper = new ModelMapper();

	static {
		modelMapper = new ModelMapper();
	}

	private ObjectMapperUtils() {
	}

	public static <D, T> D map(final T entity, Class<D> outClass) {
		if (entity == null) {
			return null;
		}
		return modelMapper.map(entity, outClass);
	}

	public static <S, D> D map(final S source, D destination) {
		modelMapper.map(source, destination);
		return destination;
	}
	
	public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
		return entityList.stream().map(entity -> map(entity, outCLass)).collect(Collectors.toList());
	}
}
