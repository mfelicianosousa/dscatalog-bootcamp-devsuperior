import React, { useEffect, useState } from 'react' ;
import { Link } from 'react-router-dom';
import ProductCard from './components/ProductCard';
import { makeRequest } from 'core/utils/request';
import { ProductsResponse } from 'core/types/Product';
import './styles.scss' ;
import ProductCardLoader from './components/Loaders/ProductCardLoader';

const Catalog = () => {

    // Quando a lista de produtos estiver disponível,
    // popular um estado no compononent, e listar os produtos dinamicamente
    const [productsResponse, setProductsResponse ] = useState<ProductsResponse>();
    // estado 
    const [isLoading, setIsLoading] = useState(false);


    //console.log(productsResponse ) ;
    // Quando o componente iniciar buscar a lista de produtos
    // limitações : Muito Verbose , não tem suporte nativo para ler o progresso de upload de arquivos
    // não tem suporte nativo para enviar query string
    useEffect(() => {
        const params ={
            page: 0,
            linesPerPage: 12
        }
        // Inicia o Loader
        setIsLoading( true ) ;
        makeRequest( {url: '/products', params } )
        .then(response => setProductsResponse( response.data ))
        .finally( ()=> {
            // finalizar o loader 
            setIsLoading( false ) ;
        });

    },[] ) ;

    return (
        <div className="catalog-container">
            <h1 className="catalog-title"> 
               Catálogo de produtos
            </h1>
            <div className="catalog-products">
               {isLoading ? <ProductCardLoader />: (
                 productsResponse?.content.map( product => (
                    <Link to={ `/products/${product.id}` }key={ product.id }>
                        <ProductCard product={ product } /> 
                    </Link> 
                ))
               )}
                
            </div>
        </div>
    ) ;
}

export default Catalog ;

