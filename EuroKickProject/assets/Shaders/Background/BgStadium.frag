varying vec2 texCoord;

uniform sampler2D m_ColorMap;
uniform sampler2D m_ColorMap2;
/*
uniform vec4 m_Color;
uniform vec4 m_Color2;
*/
uniform float g_Time;
uniform float m_ColorThreshold;
uniform float m_FlowSpeed;
uniform float m_LightIntense;

vec4 filledRect(vec2 pos,vec2 flagPos,vec2 size,vec4 flagColor){
    if (pos.x > flagPos.x - size.x && 
        pos.x < flagPos.x + size.x &&
        pos.y > flagPos.y - size.y &&
        pos.y < flagPos.y + size.y) {
            return flagColor;
    }
    return vec4(0);
}

vec4 flag(vec2 pos,vec2 flagPos,vec2 size,vec4 flagColor){
    vec2 offsetPos = pos - (flagPos - size/2);

    pos.y -= size.y * sin(offsetPos.x / size.x + g_Time);

    if (pos.x > flagPos.x - size.x && 
        pos.x < flagPos.x + size.x &&
        pos.y > flagPos.y - size.y &&
        pos.y < flagPos.y + size.y) {
            return flagColor;
    }
    return vec4(0);
}
vec4 light(vec2 pos,vec2 lightPos,vec4 lightCol,float lightRange){
    float dis = distance(pos,lightPos);
    if (dis>=lightRange) {
        return vec4(0,0,0,0);
    } else {

        vec4 col = lightCol * (1 - (dis / lightRange));
        return col;
    }
}
vec3 thing(vec2 uv, vec2 pos, vec3 color, float rad)
{
	return color * (1.0 / distance(uv, pos) * rad);	
}

vec4 particles(vec2 coord){
    float time=g_Time;
    vec2 resolution = vec2(1,1);
    float motionblur_size = 0.1;
    vec2 p=(coord.xy/resolution.x)*2.0-vec2(1.0,resolution.y/resolution.x);
    p=p*4.0;
    vec3 color = vec3(0.0);
    color += thing(p, vec2(0.0), vec3(9.0, 1.0, 0.6), 0.05);
    color += thing(p, vec2(sin(time * 99.0), cos(time * 99.0)) * 1.25, vec3(8.5, 0.5, 1.0), 0.01);
    color += thing(p, vec2(-sin(time * 99.0), -cos(time * 99.0)) * 1.25, vec3(9.5, 0.5, 1.0), 0.01);
/*
    color += thing(p, vec2(sin(time * 99.0 + 0.25), cos(time * 99.0 + 09.25)) * 0.5, vec3(6.5, 0.5, 1.0), 0.01);
    color += thing(p, vec2(sin(-time * 99.0 + 0.5), cos(time * 99.0+ 09.5)) * 0.3, vec3(6.5, 6.5, 1.0), 0.01);
    color += thing(p, vec2(sin(time * 99.0 + 0.75), cos(-time * 99.0+ 05.75)) * 0.6, vec3(0.5, 0.5, 1.0), 0.01);
    color += thing(p, vec2(sin(time * 99.0 + 1.0), cos(time * 99.0+ 16.0)) * 0.8, vec3(6.5, 6.5, 6.0), 0.01);

    color += thing(p, vec2(sin(time * 99.0 + 0.25), cos(time * 1.0 + 0.25)) * 1.5, vec3(1.0, 0.5, 0.5), 0.02);
    color += thing(p, vec2(sin(-time * 99.0 + 0.5), cos(-time * 1.0+ 7.5)) * 1.3, vec3(1.0, 0.5, 0.5), 0.02);
    color += thing(p, vec2(sin(time * 99.0 + 0.75), cos(-time * 1.0+ 4.75)) * 1.6, vec3(1.0, 0.5, 0.5), 0.02);
    color += thing(p, vec2(sin(time * 99.0 + 1.0), cos(time * 1.0+ 5.0)) * 1.8, vec3(1.0, 0.5, 0.5), 0.02);
*/
    vec2 uv = coord.xy/resolution;
    //color += texture2D(bb, vec2(uv.x, uv.y)).xyz * motionblur_size;
    color /= motionblur_size + 1.0;
    return vec4( color, 1.0 );

}
#define BLADES 6.0
#define BIAS 0.1
#define SHARPNESS 0.1
#define COLOR 0.82, 0.65, 0.4
#define BG 0.34, 0.52, 0.76

vec4 flare(vec2 pos,vec2 sunpos,vec4 lightCol){
    vec2 resolution = vec2(1,1);
    vec2 position = pos;
    vec3 color = vec3(0.0);

    float blade = clamp(pow(sin(atan(position.y - sunpos.y, position.x - sunpos.x)*BLADES)+BIAS, SHARPNESS), 0.0, 1.0);
    color = mix(vec3(0.34, 0.5, 1.0), vec3(0.0, 0.5, 1.0), (position.y + 1.0) * 0.5);
    color += (vec3(0.95, 0.65, 0.30) * 1.0 / distance(sunpos, position) * 0.075) * 1.0 - distance(vec2(0.0), position);
    color += vec3(0.95, 0.45, 0.30) * min(1.0, blade *0.7) * (1.0 / distance(sunpos, position) * 0.075);
    return vec4(color,1);
}

void main(){
    vec2 texCoord1 = texCoord;
    vec4 texColor = texture2D(m_ColorMap, texCoord1);

    vec2 texCoord2 = texCoord;
   
    float flow = g_Time/m_FlowSpeed;
    float flow2 = flow/8 + sin(g_Time)/20 *(0.3 - texCoord2.y)/0.3;
    
    vec4 m_Color = texture2D(m_ColorMap2, vec2(0.1 + flow,0.2));
    vec4 m_Color2 = texture2D(m_ColorMap2, vec2(0.1 + flow,0.3));

    if (1-texCoord2.y<0.6){
        texCoord2.x = texCoord2.x  + flow; 
        texCoord2.y = texCoord2.y * 0.8;
    } else {
        texCoord2.x =  texCoord2.x + flow2;
    }

    vec2 texCoord3 = texCoord2;
    texCoord3.x = texCoord2.x + texCoord2.y * 0.8;
    texCoord3.y = texCoord2.y /40;

    vec4 skyColor = texture2D(m_ColorMap2, texCoord2);


    // lightOfDay. SkyColor
    vec4 skyColor2 = texture2D(m_ColorMap2, texCoord2 +vec2(0,0.3));
    vec4 skyColor3 = texture2D(m_ColorMap2, texCoord3);

/*    
    if (texColor.a >= 0.5){
        texColor = vec4(texColor.rgb* (0.2+(1-skyColor2.rrr)*1.8), texColor.a);
        texColor = vec4(mix(m_Color.rgb, texColor.rgb,texColor.a), 1.0);

    }
    if (texColor.a > 0.1 && texColor.a < 0.5){
        texColor = vec4(mix(m_Color2.rgb, texColor.rgb,1 - texColor.a * 0.5), 1.0);
    }
*/
    texColor = vec4(mix(skyColor.rgb, texColor.rgb,texColor.a), 1.0);

    //Flags layer
/*
    texColor = texColor +flag(texCoord,vec2(0.5,0.4),vec2(0.005,0.004),vec4(1,0,0,1));
    texColor = texColor +flag(texCoord,vec2(0.1,0.41),vec2(0.005,0.004),vec4(1,0,0,1));
    texColor = texColor +flag(texCoord,vec2(0.24,0.42),vec2(0.005,0.004),vec4(1,0,0,1));
    texColor = texColor +flag(texCoord,vec2(0.31,0.42),vec2(0.005,0.004),vec4(1,0,0,1));
    texColor = texColor +flag(texCoord,vec2(0.91,0.42),vec2(0.005,0.004),vec4(1,0,0,1));
    texColor = texColor +flag(texCoord,vec2(0.87,0.42),vec2(0.003,0.002),vec4(1,0,0,1));
*/
/*
    float lightOfDay = (0.9+(skyColor3.rrr+(1-texCoord.y))*sin(g_Time/4))*abs(sin(g_Time/20));
    lightOfDay = 1;
    //texColor = vec4(texColor.rgb *  (1- m_LightIntense * lightOfDay), 1.0);
    //texColor = vec4(mix(m_Color2.rgb, texColor.rgb,0.5*(texColor.a-0.1)), 1.0);
*/
    texColor = texColor + light(texCoord,vec2(0.85,0.5),vec4(1,1,1,1),0.1)* ( 0.4 + 0.6 * abs(sin(g_Time/10)));
    texColor = texColor + light(texCoord,vec2(0.95,0.55),vec4(1,1,1,1),0.1)* ( 0.4 + 0.6 * abs(sin(g_Time/10)));
    //texColor = texColor + flare(texCoord,vec2(0.35,0.55),vec4(1,1,1,1));
    //texColor = texColor + particles(texCoord);

    gl_FragColor = texColor;
}