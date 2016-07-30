

function show_cart()
{
    $.ajax({
        url:base_url+'item/action_cartInfo',
        dataType:'json',
        success:function(data)
        {
            console.log(data.cart);
            data = data.cart;
            
            $('#cart').modal('show');
            if(data.length == 0)
                $('#cart').find('.modal-footer').find('#order').attr('disabled','disabled');
            else
                $('#cart').find('.modal-footer').find('#order').removeAttr('disabled');

            $modal = $('#cart').find('.modal-body').find('.table');
            $modal.find("tr[id!='heading']").remove();
            for(var i=0; i < data.length; i++)
            {
                console.log(data[i]);
                $modal.append("<tr>\
                    <td style='display:none'>"+data[i].bookID+"</td>\
                    <td>"+data[i].title+"</td>\
                    <td>"+data[i].author+"</td>\
                    <td>"+data[i].press+"</td>\
                    <td>"+data[i].price/100+"</td>\
                    <td>"+ "<button class='btn btn-xs modify'onclick='minusAmount(" + i + "," + data[i].bookID  + "," + data[i].price +
                    ")'> <i class='fa fa-minus' aria-hidden='true'></i></button>"+
                        "<span class='amount'>" + data[i].amount + "</span>" +
                    "<button class='btn btn-xs modify' onclick='addAmount(" + i + "," + data[i].bookID + "," + data[i].price +
                    ")'><i class='fa fa-plus' aria-hidden='true'></i></button></td>" +
                    "<td> <span class='price'>" + data[i].price*data[i].amount/100+"</span></td>\
                    <td>" + 
                    "<button class='btn btn-default btn-xs modify' \
                        onclick='removeBookInCart("+ i + "," + data[i].bookID + ")'><i class='fa fa-times' aria-hidden='true'></i></button></td></tr> "
                    );
            }
        }
    }); 
}

function minusAmount(book_index, book_ID, price) {
    // console.log(book_index, book_ID, price);
    book_index = parseInt(book_index)+1;
    price = parseInt(price);
    var tuple = $('#cart .modal-body').find('tr:eq('+book_index+')');
    var amount = tuple.find('.amount');
    var num = parseInt(amount.html());
    if(num == 1)
        return;
    $('.modify').addClass('disabled');
    $.post({
        url: base_url + "item/action_changeAmountInCart",
        data: {
            'bookID':book_ID,
            'change':-1
        },
        success: function(msg) {
            if(msg.success) {
                num -= 1;
                amount.html(num);
                tuple.find('.price').html(num*price/100);
            }
            $('.modify').removeClass('disabled');
        }
    });
    
}

function addAmount(book_index, book_ID, price) {
    // console.log(book_index, book_ID);
    $('.modify').addClass('disabled');

    book_index = parseInt(book_index)+1;
    price = parseInt(price);
    var tuple = $('#cart .modal-body').find('tr:eq('+book_index+')');
    var amount = tuple.find('.amount');
    var num = parseInt(amount.html());

    $.post({
        url: base_url + "item/action_changeAmountInCart",
        data: {
            'bookID':book_ID,
            'change':1
        },
        success: function(msg) {
            if(msg.success) {
                num += 1;
                amount.html(num);
                tuple.find('.price').html(num*price/100);
            }
            $('.modify').removeClass('disabled');
        }
    });
}

function removeBookInCart(book_index, book_ID) {
    console.log(book_index, book_ID);
    $.post({
        url: base_url + "item/action_removeBookInCart",
        data: {
            'bookID':book_ID,
        },
        success: function(msg) {
            if(msg.success) {
                book_index = parseInt(book_index)+1;
                $('#cart .modal-body').find('tr:eq('+book_index+')').remove();
                var remain = $('#cart .modal-body').find('tr').length;
                if(remain == 1) {
                    $('#order').addClass('disabled');
                }
            }
        }
    });
}

function showTip(tip, type) {
    var $tip = $('#tip');
    $tip.attr('class', 'alert alert-' + type).text(tip).css('margin-left', - $tip.outerWidth() / 2).fadeIn(500).delay(1000).fadeOut(500);
}