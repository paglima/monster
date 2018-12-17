package com.paglima.monster.domain;


public enum Brand {
    ACOM("ACOM", 2, "02", "Americanas"),
    SUBA("SUBA", 3, "03", "Submarino"),
    SHOP("SHOP", 1, "01", "Shoptime"),
    SOUB("SOUB", 7, "07", "Sou Barato");

    private final String acronym;
    private final Integer brandId;
    private final String codValue;
    private final String name;

    Brand(String value, Integer brandId, String codValue, String name) {
        this.acronym   = value;
        this.brandId = brandId;
        this.codValue = codValue;
        this.name = name;
    }

    public String getValue() {
        return acronym;
    }
    
    public Integer getBrandId() {
        return brandId;
    }
    
    public String getCodValue() {
        return codValue;
    }
    
    public String getName() {
		return name;
	}
    
    public static String getAcronymByBrandId(Integer brandId) throws Exception {
    	for (Brand brand : Brand.values()) {
			if (brand.getBrandId() == brandId) {
				return brand.getValue();
			}
		}
    	
    	throw new Exception("Nao foi possivel obter o acronimo para o brandId passado.");
    }
    
    public static Brand getBrandByBrandId(Integer brandId) throws Exception {
    	for (Brand brand : Brand.values()) {
			if (brand.getBrandId() == brandId) {
				return brand;
			}
		}
    	
    	throw new Exception("Nao foi possivel obter o acronimo para o brandId passado.");
    }
    
    public static Integer getBrandIdByAcronym(String brand) throws Exception {
    	Integer brandId = null;
    	
    	try {
			brandId = Brand.valueOf(brand).getBrandId();
		} catch (Exception e) {
			throw new Exception("Nao foi possivel obter o brandId para o acronimo passado.");
		}
    	
    	return brandId;
    }
    
    public static Brand getBrandByAcronym(String acronym) throws Exception {
    	Brand brand = null;
    	
    	try {
			brand = Brand.valueOf(acronym);
		} catch (Exception e) {
			throw new Exception("Nao foi possivel obter a brand para o acronimo passado.");
		}
    	
    	return brand;
    }
    
    public static String getCodValueByBrandId(Integer brandId) throws Exception {
    	if (Brand.ACOM.brandId.equals(brandId)) {
    		return Brand.ACOM.codValue;
    	}
    	
    	if (Brand.SUBA.brandId.equals(brandId)) {
    		return Brand.SUBA.codValue;
    	}
    	
    	if (Brand.SOUB.brandId.equals(brandId)) {
    		return Brand.SOUB.codValue;
    	}
    	
    	if (Brand.SHOP.brandId.equals(brandId)) {
    		return Brand.SHOP.codValue;
    	}
    	
    	throw new Exception("Nao foi possivel obter o codValue para o brandId passado.");
    }
}
