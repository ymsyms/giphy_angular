import { NgForm } from '@angular/forms';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { GiphyModel } from './giphyModel';
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/take';


@Injectable()
export class GiphyService {

    constructor (private http: HttpClient) {}

    giphySearch(searchWords: string): Promise<any> {
        let queryString = new HttpParams().set('q',searchWords);

        return (this.http.get('https://api.giphy.com/v1/gifs/search?api_key=x2OOZvBBVmnyNvKN7hV3mZb6UzBKy1zJ',{params: queryString}).toPromise());
    }

    getLikeGiphys(userid: String): Promise<any> {
        return (this.http.get('http://localhost:8080/GiphyApplication_CA/getGiphyByUserId/'+userid)
            .take(1)
            .toPromise());
    }

    addToLikeList (data: GiphyModel) : Promise<any> {
        let queryString = new HttpParams()
            .set('userid',data.userid)
            .set('url',data.url)
            .set('title',data.title);
        
        return (this.http.get('http://localhost:8080/GiphyApplication_CA/addGiphyToFavorite',{params:queryString})
            .toPromise());
    }
}