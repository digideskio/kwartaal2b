cursor lc_ord is
		select {{tableName}}.{{fieldName}}
		FROM {{tableName}}
		WHERE {{primairyKey}} = :new.{{foreignKey}};
		l_returnvalue {{tableName}}.{{fieldName}}%type;