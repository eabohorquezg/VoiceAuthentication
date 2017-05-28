var tiempo = 3;

function cuentaRegresiva(){                
    if (tiempo > 0){
        $.jGrowl("Grabando: Quedan "+tiempo+" segundos",{ life : 900});
        tiempo--;
        document.fcuentareg.tiempoact.value=tiempo;
        setTimeout("cuentaRegresiva()",1000);
    }
    else{
        tiempo=0;
    }
}