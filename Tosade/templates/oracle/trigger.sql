create or replace trigger brg_{{schemaCode}}_{{tableCode}}_trigger
    before delete or insert or update
    on {{tableName}}
    for each row
declare
    l_oper varchar2(3);
    l_error_stack varchar2(4000);
begin
    if inserting
    then
            l_oper := 'INS';
    elsif updating
    then
            l_oper := 'UPD';
    elsif deleting
    then
            l_oper := 'DEL';
    end if;
    {{triggers}}
    if l_error_stack is not null
    then
            raise_application_error(-20800, l_error_stack);
    end if;
end brg_{{schemaCode}}_{{tableCode}}_trigger;